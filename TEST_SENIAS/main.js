//Definir las variables para el procesamiento con los elementos del html
const videoElement = document.getElementsByClassName('input_video')[0];
const canvasElement = document.getElementsByClassName('output_canvas')[0];
const canvasCtx = canvasElement.getContext('2d');

//Crear enlace con el websocket
const socket = new WebSocket("ws://localhost:8080/socket");
socket.binaryType = "arraybuffer";

let canvasIsReady = false

socket.onopen = function (event) {
   console.log("Connected to the web socket.")

   FPS = 1
   //Enviar un número de frames específicos

   setInterval(() => {
      if (canvasCtx && canvasIsReady){
         var dataURL = canvasElement.toDataURL();
         
         var img = new Image();
         img.src = dataURL;

         img.onload = function() {
             var canvas = document.createElement('canvas');
             var ctx = canvas.getContext('2d');
             
             // Redimensionar la imagen a 256x256
             canvas.width = 256;
             canvas.height = 256;
             ctx.drawImage(img, 0, 0, 256, 256);
             
             var dataURL = canvas.toDataURL();
             fetch(dataURL)
               .then(res => res.blob())
               .then(blob => {
                   blob.arrayBuffer().then(function(buffer){
                     console.log(buffer)
                     socket.send(buffer)
                     //socket.close();
                   })
               });
         };         
      }
   }, 1000 / FPS);
};


//Función para obtener el video en tiempo real sin perder contenido
//por redimensionar
function setCanvaMediaVideoSize() {
   //Obtener las dimensiones (ancho y alto) del video
   let videoWidth = videoElement.videoWidth;
   let videoHeight = videoElement.videoHeight;

   //Calcular la proporción entre ancho y alto para mantener el aspecto
   let aspectRatio = videoWidth / videoHeight;

   //Ajustar las dimensiones del canvas para mantener el aspecto del video
   let canvasWidth = canvasElement.offsetWidth;
   let canvasHeight = canvasWidth / aspectRatio;

   //En caos de que el tamanio no se acople, utilizar el aspecto calculado
   //para evitar malos redimensionamientos
   if (canvasHeight > canvasElement.offsetHeight) {
      canvasHeight = canvasElement.offsetHeight;
      canvasWidth = canvasHeight * aspectRatio;
   }

   //Redimensionando el canvas para que coincida con las dimensiones calculadas
   canvasElement.width = canvasWidth;
   canvasElement.height = canvasHeight;   
}


//Funcion para obtener los resultados del procesamiento de mediapipe
function onResults(results) {
   setCanvaMediaVideoSize();
   
   //Redimensionar el canva al video captado en por la cámara
   setCanvaMediaVideoSize()
   
   //Limpiar el canvas
   canvasCtx.clearRect(0, 0, canvasElement.width, canvasElement.height);     
   
   canvasElement.style.border = "1px solid red";

   //Llenar el canvas con el color de fondo negro y el tamanio total al que corresponde
   canvasCtx.fillStyle = '#FFFFFF';

   //Si se quiere mantener el mismo video de fondo, descomentar la linea con drawImage()
   canvasCtx.fillRect(0, 0, canvasElement.width, canvasElement.height)
   //canvasCtx.drawImage(videoElement, 0, 0);

   //Factores para auto ajuste de tamanio
   baseAutosizeFactor = 5
   movementAutosizeFactor = 5
   
   if (results.multiHandLandmarks) {
      for (const landmarks of results.multiHandLandmarks) {

         //Obtener la coordenada z (profundidad) de todos los puntos
         let zCoordinates = landmarks.map(landmark => Math.abs(landmark.z));

         //Obtener la minima coordenada z
         let minZ = Math.min(...zCoordinates);
         //let maxZ = Math.max(...zCoordinates);

         //Normalizar el valor minimo de z a un valor positio o negativo
         let movementDirection = minZ < 0 ? -1 : 1;

         //Recalcular el autoajuste utilizando los factores
         let lineWidth = baseAutosizeFactor + (movementDirection * movementAutosizeFactor);

         //Dibujar las lineas y puntos por la captacion de la mano
         drawConnectors(canvasCtx, landmarks, HAND_CONNECTIONS, {color: '#000000', lineWidth: lineWidth});

         //Si se requiere dibujar los puntos, modificar la division del radio
         drawLandmarks(canvasCtx, landmarks, {color: '#000000', lineWidth: lineWidth / 2, radius: lineWidth / 4});
      }
   }

   canvasIsReady = true
}


//Cargar la libreria para usar mediapipe
const hands = new Hands({locateFile: (file) => {
 return `https://cdn.jsdelivr.net/npm/@mediapipe/hands/${file}`;
}});

//Establecer los parametros utilizados para la configuracion de mediapipe
hands.setOptions({
   maxNumHands: 2, //Número máximo de manos aceptables o que se captaran
   modelComplexity: 1, //1 prioriza la captación antes que la velocidad, menor valor prioriza velocidad
   minDetectionConfidence: 0.6, //Menor valor prioriza detección menos optima, mayor valor prioriza la detección
   minTrackingConfidence: 0.6 //Menor valor prioriza el seguimiento menos optimo, mayor valor prioriza el seguimiento correcto
});

//Definir el metodo de los resultados que equivale a la captacion del video
hands.onResults(onResults);

//Definir la captacion de video de la camara y empezarla
const camera = new Camera(videoElement, {
   onFrame: async () => {
      await hands.send({image: videoElement});
   },
   width: 1280,
   height: 720
});
camera.start();

//Adaptar el canvas cuando cargue la camara
videoElement.addEventListener('loadedmetadata', () => {
   const videoDimensions = videoElement.getBoundingClientRect();
   canvasElement.style.width = videoDimensions.width + "px";
   canvasElement.style.height = videoDimensions.height + "px";
  });