package seccionCritica;

public class Servidor {

    public static void main(String args[]) throws IOException {

        Scanner sc = new Scanner(System.in);
        boolean salida = false;
        boolean escribir = false;
        ServerSocket servidor;
        servidor = new ServerSocket(6000);

        //crear nuevo socket para que cliente se conecte al servidor
        Socket cliente = new Socket();
        cliente = servidor.accept(); //server conectado con el socket y viceversa.

        // CREO FLUJO DE SALIDA AL CLIENTE
        OutputStream outputsalida = null;
        outputsalida = cliente.getOutputStream();
        DataOutputStream flujoSalida = new DataOutputStream(cliente.getOutputStream());


        // CREO FLUJO DE ENTRADA AL CLIENTE
        InputStream entrada = null;
        entrada = cliente.getInputStream();
        DataInputStream flujoEntrada = new DataInputStream(cliente.getInputStream());

        while(!salida) {

            while(escribir) {
                System.out.println("Escribe un mensaje a Cliente: ");
                // ENVIO UN SALUDO AL SERVIDOR
                String texto = sc.nextLine();
                flujoSalida.writeUTF(texto);
                //strin text, flujo salida. condicional que mira si el texto contiene la letra clave
                if(texto.contains("cambio y corto")){
                    System.out.println("Hasta luegui Servidor: ");
                    escribir = false;
                    salida = true;
                } else if(texto.contains("cambio")) {
                    System.out.println("Pasando el control a Cliente: ");
                    escribir = false;
                }

            }

            while(!escribir) {
                String textoEntrada = flujoEntrada.readUTF();

                // EL SERVIDOR ME ENVIA UN MENSAJE
                System.out.println("Recibiendo del CLIENTE: \n\t" + textoEntrada);
                if(textoEntrada.contains("cambio y corto")){
                    System.out.println("Hasta luegui Servidor: ");
                    salida = true;
                } else if(textoEntrada.contains("cambio")) {
                    escribir = true;
                }

            }
        }

        flujoEntrada.close();
        flujoSalida.close();
        cliente.close();
    }// Fin de main

}
