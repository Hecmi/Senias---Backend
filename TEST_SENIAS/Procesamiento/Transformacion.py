import cv2
import mediapipe as mp
import numpy as np

#Inicializar mediapipe para el uso de la solución con manos
mpHands = mp.solutions.hands
hands = mpHands.Hands()
mpDraw = mp.solutions.drawing_utils

#Cargar la imagen desde un archivo
imagen = cv2.imread("C:/Users/LUIS CASANOVA/Dropbox (Anterior)/Mi PC (DESKTOP-P50TEI6)/Desktop/7MO SEMESTRE\APLICACIONES DISTRIBUIDAS/TF_SENIAS/Procesamiento/Frame-1706309347.png")

#Convertir la imagen a RGB.
imagenRGB = cv2.cvtColor(imagen, cv2.COLOR_BGR2RGB)
imagenNF = np.zeros_like(imagen)

#Procesar la imagen RGB con mediapipe
results = hands.process(imagenRGB)

#Si se detectaron puntos clave de la mano, dibujarlos en la imagen
if results.multi_hand_landmarks:
    for handLandmarks in results.multi_hand_landmarks:
        # Dibujar los puntos clave de la mano
        imagen = cv2.cvtColor(imagen, cv2.COLOR_BGR2RGB)

        #Crear una copia de la imagen con las mismas dimensiones pero 
        #con el fondo totalmente negro
        mpDraw.draw_landmarks(imagenNF, handLandmarks, mpHands.HAND_CONNECTIONS)
        imagen = cv2.cvtColor(imagenNF, cv2.COLOR_RGB2BGR)

#Mostrar la imagen con la detección de manos
cv2.imshow('Imagen con detección de manos', imagenNF)
cv2.waitKey(0)
cv2.destroyAllWindows()
