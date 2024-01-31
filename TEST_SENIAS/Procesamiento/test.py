import cv2
import mediapipe as mp
import numpy as np

#Inicializar medipipe para el uso de la solucion con manos.
mpHands = mp.solutions.hands
hands = mpHands.Hands()
mpDraw = mp.solutions.drawing_utils

#Inicializar la camara con openCV (cv2)
cam = cv2.VideoCapture(0)

def getCuadrantes(alto, ancho):
    altoCentro = alto // 2
    anchoCentro  = ancho //2 
    centro = (altoCentro, anchoCentro)

    # primerCuadrante = [(anchoCentro, 0), (ancho, altoCentro)]
    # segundoCuadrante = [(0, 0), (anchoCentro, altoCentro)]
    # tercerCuadrante = [(0, altoCentro), (anchoCentro, alto)]
    # cuartoCuadrante = [(0, altoCentro), (ancho, alto)]
    primerCuadrante = [(altoCentro, 0), (alto, anchoCentro), 3]
    segundoCuadrante = [(0, 0), (altoCentro, anchoCentro), 2]
    tercerCuadrante = [(0, anchoCentro), (altoCentro, ancho), 1]
    cuartoCuadrante = [(altoCentro, 0), (alto, ancho), 4]

    cuadrantes = [primerCuadrante, segundoCuadrante, tercerCuadrante, cuartoCuadrante]
    return cuadrantes

def getPuntoCuadrante(punto, cuadrantes):
    x = punto[0]
    y = punto[1]

    for i in range(len(cuadrantes)):
        cuadrante = cuadrantes[i]
        puntoInicial = cuadrante[0]
        puntoFinal = cuadrante[1]

        ix = puntoInicial[0]
        iy = puntoInicial[1]
        fx = puntoFinal[0]
        fy = puntoFinal[1]

        
        if  x >= ix and x <= fx and y >= iy and y <= fy:
            print(punto, cuadrante, (i + 1))
            break



#Captar la imagen hasta que se presione una tecla especifico
while True:
    #Captar la imagen correspondiente a un frame especifico
    fue_captado, imagen = cam.read()

    #Variables para definir el punto central de la mano
    dx = 0
    dy = 0
    altura = 0
    ancho = 0

    #Verificar si se logro captar la imagen correctamente
    if fue_captado:
        altura, ancho, _ = imagen.shape        
        cuadrantes = getCuadrantes(ancho, altura)

        #Convertir la imagen a RGB.
        imagenRGB = cv2.cvtColor(imagen, cv2.COLOR_BGR2RGB)

        #Crear una copia de la imagen con las mismas dimensiones pero 
        #con el fondo totalmente negro
        imagenNF = np.zeros_like(imagenRGB)

        #Procesar la imagen RGB con mediapipe
        results = hands.process(imagenRGB)
        
        #Si se detectaron puntos clave de la mano colocarlos en la imagen
        if results.multi_hand_landmarks:
            for handLandmarks in results.multi_hand_landmarks:

                #Dibujar los puntos clave de la mano
                # mpDraw.draw_landmarks(imagenNF, handLandmarks, mpHands.HAND_CONNECTIONS)
                mpDraw.draw_landmarks(
                    imagenNF,
                    handLandmarks,
                    mpHands.HAND_CONNECTIONS,
                    landmark_drawing_spec=mpDraw.DrawingSpec(color=(47, 146, 213), thickness=6, circle_radius=3),
                    connection_drawing_spec=mpDraw.DrawingSpec(color=(255, 255, 255), thickness=3, circle_radius=3)
                )
                
                #Calcular el centro de la mano para colocarlo en la imagen (promedio o media)
                dx, dy = 0, 0
                numeroPunto = 0
                for id, punto in enumerate(handLandmarks.landmark):
                    h, w, c = imagenNF.shape
                    dx += punto.x * w
                    dy += punto.y * h
                    numeroPunto += 1
                    
                dx /= numeroPunto
                dy /= numeroPunto

                #Aniadir el centro de la marca   
                getPuntoCuadrante((dx, dy), cuadrantes)
                cv2.circle(imagenNF, (int(dx), int(dy)), 3, (47, 146, 213), 6)


        imagenNF = cv2.cvtColor(imagenNF, cv2.COLOR_BGR2RGB)
        #Punto central
        cv2.circle(imagenNF, (ancho // 2, altura // 2), 5, (255, 255, 255), 1)

        # cv2.rectangle(imagenNF, cuadrantes[0][0], cuadrantes[0][1], (255, 255, 255), 1)
        # cv2.rectangle(imagenNF, cuadrantes[1][0], cuadrantes[1][1], (255, 255, 255), 1)
        # cv2.rectangle(imagenNF, cuadrantes[2][0], cuadrantes[2][1], (255, 255, 255), 1)
        # cv2.rectangle(imagenNF, cuadrantes[3][0], cuadrantes[3][1], (255, 255, 255), 1)
        # cv2.circle(imagenNF, cuadrantes[1][0], 5, (255, 255, 255), 1)
        # cv2.circle(imagenNF, cuadrantes[1][1], 5, (255, 255, 255), 1)
        
        
        #Definir las lineas que dividen los cuadrantes.
        # cv2.line(imagenNF, (ancho // 2, 0), (ancho // 2, altura), (255, 255, 255), 1)
        # cv2.line(imagenNF, (0, altura // 2), (ancho, altura // 2), (255, 255, 255), 1)

        #Mostrar la imagen en la ventana
        cv2.imshow('Manos referenciadas D:', imagenNF)

    #Finalizar la captacion cuando se presione la tecla s
    if cv2.waitKey(1) & 0xFF == ord('s'):
        break

# Destruir la ventana
cv2.destroyAllWindows()


