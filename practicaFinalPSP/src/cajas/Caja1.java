package cajas;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Caja1 {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        boolean salida = false;
        boolean escribir = true;
        String Host = "localhost";
        int Puerto = 6000;//puerto remoto


        System.out.println("PROGRAMA CLIENTE INICIADO....");
        Socket Caja1 = new Socket(Host, Puerto);

        // CREO FLUJO DE ENTRADA AL SERVIDOR
        DataInputStream flujoEntrada = new DataInputStream(Caja1.getInputStream());

        // CREO FLUJO DE SALIDA AL SERVIDOR
        DataOutputStream flujoSalida = new DataOutputStream(Caja1.getOutputStream());

        //Controlar cuando escucha y cuando escribe mediante booleanos y utilizando un while

        //Tenemos que tener en cuenta que los booleanos cambian cuando decimos palabras claves que son cambio(cambia a quien escribe), cambio y corto(que se sale del bucle while y se cierra la conexion)
        while(!salida) {

            while(escribir) {
                System.out.println("Escribe un mensaje a Servidor: ");
                // ENVIO UN SALUDO AL SERVIDOR
                String texto = sc.nextLine();
                flujoSalida.writeUTF(texto);
                //strin text, flujo salida. condicional que mira si el texto contiene la letra clave
                if(texto.contains("cambio y corto")){
                    System.out.println("Hasta luegui Cliente: ");
                    escribir = false;
                    salida = true;
                } else if(texto.contains("cambio")) {
                    System.out.println("Pasando el control a Servidor: ");
                    escribir = false;
                }


            }

            while(!escribir) {
                String textoentrada = flujoEntrada.readUTF();
                // EL SERVIDOR ME ENVIA UN MENSAJE
                System.out.println("Recibiendo del SERVIDOR: \n\t" + textoentrada);
                if(textoentrada.contains("cambio y corto")){
                    System.out.println("Hasta luegui Cliente: ");
                    salida = true;
                } else if(textoentrada.contains("cambio")) {
                    escribir = true;
                }

            }
        }

        // CERRAR STREAMS Y SOCKETS
        flujoEntrada.close();
        flujoSalida.close();
        Caja1.close();

    }// fin de main
}
