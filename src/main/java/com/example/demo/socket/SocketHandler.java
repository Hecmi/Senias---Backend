/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.socket;

import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.imageio.ImageIO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 *
 * @author LUIS CASANOVA
 */
@Component
public class SocketHandler extends AbstractWebSocketHandler  {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("New WebSocket Connection Established: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        System.out.println("WebSocket Connection Closed: " + session.getId() + ", Status: " + status.toString());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        System.out.println("New Text Message Received from Session ID: " + session.getId() + ", Payload: " + message.getPayload());
        session.sendMessage(message);
    }

   @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
        System.out.println("New Binary Message Received from Session ID: " + session.getId() + ", Payload Length: " + message.getPayloadLength() + " bytes");

        // Convert the payload to a byte array
        ByteBuffer buffer = message.getPayload();
        int payloadLength = message.getPayloadLength();
        byte[] payloadBytes = new byte[payloadLength];

        if (buffer.limit() < payloadLength) { // Check the buffer's limit
            throw new IllegalArgumentException("Buffer limit is less than payload length");
        }

        buffer.get(payloadBytes);
        // Get the current date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = dateFormat.format(new Date());

        // Save the payloadBytes as an image file with timestamp
        try (FileOutputStream fos = new FileOutputStream("received_image_" + timestamp + ".jpg")) {
            fos.write(payloadBytes);
        } catch (IOException e) {
            // Handle the exception
            e.printStackTrace();
        }
        
        System.out.println(Arrays.toString(payloadBytes)); // Print the payload bytes

        session.sendMessage(new BinaryMessage(payloadBytes)); // Send the payload bytes as a new BinaryMessage
    }


}