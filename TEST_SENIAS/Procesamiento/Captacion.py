# import cv2
# from rembg import remove
# import time

# #Inicializa la camara
# cap = cv2.VideoCapture(0)

# #Contador para controlar el tiempo en el que se toma la foto
# counter = 0

# while True:
#     #Se captura el frame 
#     fue_captado, frame = cap.read()
    
#     if fue_captado:
#         # Muestra el frame resultante dela captacion del video
#         cv2.imshow('D:', frame)

#         #Si el contador llega a x valor, procesa la imagen
#         if counter >= 30:
#             # Nuestras operaciones en el frame vienen aquí
#             frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
#             frame = remove(frame)

#             #Crear el  nombre del archivo y guardarlo
#             filename = 'Frame-{0}.png'.format(int(time.time()))
#             cv2.imwrite(filename, frame)

#             #Reiniciar el contador
#             counter = 0

#         #Para finalizar el programa definir una letra que lo cierre
#         if cv2.waitKey(1) & 0xFF == ord('s'):
#             break

#     # Incrementa el contador
#     counter += 1

# #Librear la camara y "destruir" la ventana de captacion
# cap.release()
# cv2.destroyAllWindows()


import cv2
import mediapipe as mp
import numpy as np

# Initialize mediapipe for hand usage
cap = cv2.VideoCapture(0)
segmentacion = mp.solutions.selfie_segmentation.SelfieSegmentation(model_selection = 1)

while True:
    # Capture the frame
    fue_captado, imagen = cap.read()
    altura, ancho, _ = imagen.shape
    # Convert the image to RGB
    imagenRGB = cv2.cvtColor(imagen, cv2.COLOR_BGR2RGB)

    # Process the image
    resultado = segmentacion.process(imagenRGB)
    mascara = resultado.segmentation_mask

    # Apply Gaussian blur to the mask
    mascara = cv2.GaussianBlur(mascara, (3, 3), 0)

    # Adjust the threshold of the mask
    rsm = np.stack((mascara,)* 3, axis = -1)
    condicion = rsm > 0.2
    condicion = np.reshape(condicion, (altura, ancho, 3))

    # Postprocessing of the mask
    mascara = cv2.GaussianBlur(mascara, (5, 5), 0)

    # Generate the final image by replacing the background of the original image with black
    imagenR = np.where(condicion, imagen, [0, 0, 0]).astype(np.uint8)

    # Show the image with the background removed
    cv2.imshow('Image with background removed', imagenR)
    if cv2.waitKey(1) & 0xFF == ord('s'):
        break

cv2.waitKey(0)
cv2.destroyAllWindows()
