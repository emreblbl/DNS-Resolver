package io.github.emreblbl.dnsresolver.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DnsClient {

    private static final int BUFFER_SIZE = 512;

    /**
     * Sends a UDP message to the specified IP and port and receives the response.
     *
     * @param query The DNS query as a byte array.
     * @param ip    The IP address to send the query to.
     * @param port  The port number to send the query to.
     * @return The response from the server as a byte array.
     * @throws IOException if there's an issue with network communication.
     */
    public byte[] sendUdpMessage(byte[] query, InetAddress ip, int port) throws IOException {
        DatagramPacket packet = createDatagramPacket(query, ip, port);
        return sendAndReceive(packet);
    }

    private DatagramPacket createDatagramPacket(byte[] data, InetAddress ip, int port) {
        return new DatagramPacket(data, data.length, ip, port);
    }

    private byte[] sendAndReceive(DatagramPacket packet) throws IOException {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.send(packet);
            byte[] buf = new byte[BUFFER_SIZE];
            DatagramPacket response = new DatagramPacket(buf, buf.length);
            socket.receive(response);
            return response.getData();
        }
    }
}
