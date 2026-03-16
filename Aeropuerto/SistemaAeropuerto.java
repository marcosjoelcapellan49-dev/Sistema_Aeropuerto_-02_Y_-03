import java.util.*; 

class Avion {
    String codigo;
    String modelo;
    int capacidad;

    public Avion(String codigo, String modelo, int capacidad) {
        this.codigo = codigo;
        this.modelo = modelo;
        this.capacidad = capacidad;
    }

    public void mostrar() {
        System.out.printf("%-10s %-30s %-10d\n", codigo, modelo, capacidad);
    }
}

class Vuelo {
    String numero;
    String destino;
    String aerolinea;
    String tipo;
    Avion avion;

    public Vuelo(String numero, String destino, String aerolinea, String tipo, Avion avion) {
        this.numero = numero;
        this.destino = destino;
        this.aerolinea = aerolinea;
        this.tipo = tipo;
        this.avion = avion;
    }

    public void mostrar() {
        System.out.printf("%-8s %-20s %-25s %-10s %-30s\n",
                numero, destino, aerolinea, tipo, avion.modelo);
    }
}

class Boleto {
    String numero, pasajero, aerolinea;
    String origen, destino;
    String origenCodigo, destinoCodigo;
    String clase, tipoViaje;
    String asiento, fecha, hora, gate;
    double precio;

    public Boleto(String numero, String pasajero, String aerolinea,
                  String origen, String destino,
                  String origenCodigo, String destinoCodigo,
                  String clase, String tipoViaje,
                  String asiento, String fecha, String hora, String gate,
                  double precio) {

        this.numero = numero;
        this.pasajero = pasajero;
        this.aerolinea = aerolinea;
        this.origen = origen;
        this.destino = destino;
        this.origenCodigo = origenCodigo;
        this.destinoCodigo = destinoCodigo;
        this.clase = clase;
        this.tipoViaje = tipoViaje;
        this.asiento = asiento;
        this.fecha = fecha;
        this.hora = hora;
        this.gate = gate;
        this.precio = precio;
    }

    public void imprimir() {
        System.out.println("\n================================================================================");
        System.out.println(" AIR COMPANY                          BOARDING PASS");
        System.out.println("================================================================================");
        System.out.printf(" Passenger: %-25s  Carrier: %-20s Flight: %-10s Class: %-10s\n",
                pasajero, aerolinea, numero, clase);
        System.out.printf(" From: %-3s %-20s  To: %-3s %-20s\n",
                origenCodigo, origen, destinoCodigo, destino);
        System.out.printf(" Date: %-12s  Boarding: %-10s  Seat: %-6s\n",
                fecha, hora, asiento);
        System.out.printf(" Gate: %-6s   Travel: %-12s   Price: $%.2f\n",
                gate, tipoViaje, precio);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(" BARCODE");
        System.out.println(" |||| |||| ||| |||||| |||| ||||");
        System.out.println("================================================================================");
        System.out.println(" PASSENGER COPY");
        System.out.printf(" %-20s  Flight:%-10s  Seat:%-6s\n",
                pasajero, numero, asiento);
        System.out.printf(" %-3s -> %-3s     Date:%-10s  Gate:%-5s\n",
                origenCodigo, destinoCodigo, fecha, gate);
        System.out.println(" Boarding Time: " + hora);
        System.out.println("================================================================================");
    }
}

public class SistemaAeropuerto {
    static Random random = new Random();

    // Control de asientos por avión
    static HashMap<String, HashSet<String>> asientosPorAvion = new HashMap<>();

    // Arrays de nombres y apellidos para generar pasajeros
    static String[] nombres = {"Miguel", "Carlos", "Sofía", "Valeria", "Juan", "Ana", "Luis", "Isabella",
            "David", "Camila", "José", "María", "Pedro", "Gabriela", "Andrés", "Lucía"};
    static String[] apellidos = {"Pérez", "Ramírez", "Gómez", "Hernández", "López", "Martínez",
            "Rodríguez", "Santos", "Torres", "Flores", "Vargas", "Mendoza", "Castillo", "Morales"};

    static String generarNombrePasajero() {
        String nombre = nombres[random.nextInt(nombres.length)];
        String apellido = apellidos[random.nextInt(apellidos.length)];
        return nombre + " " + apellido;
    }

    static String generarNumeroBoleto() {
        return "TK" + (100000 + random.nextInt(900000));
    }

    static String generarNumeroVuelo() {
        return "FL" + (100 + random.nextInt(900));
    }

    static String generarAsiento(String codigoAvion) {
        HashSet<String> ocupados = asientosPorAvion.getOrDefault(codigoAvion, new HashSet<>());
        String asiento;
        do {
            int fila = 1 + random.nextInt(30);
            char letra = (char) ('A' + random.nextInt(6));
            asiento = fila + "" + letra;
        } while (ocupados.contains(asiento));
        ocupados.add(asiento);
        asientosPorAvion.put(codigoAvion, ocupados);
        return asiento;
    }

    static String generarHora() {
        int h = 1 + random.nextInt(23);
        int m = random.nextInt(60);
        return String.format("%02d:%02d", h, m);
    }

    static String generarFecha() {
        int d = 1 + random.nextInt(28);
        int m = 1 + random.nextInt(12);
        return String.format("%02d/%02d/2026", d, m);
    }

    static String generarGate() {
        char letra = (char) ('A' + random.nextInt(5));
        int num = 1 + random.nextInt(30);
        return letra + "" + num;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayList<Avion> aviones = new ArrayList<>();
        ArrayList<Vuelo> vuelos = new ArrayList<>();
        ArrayList<Boleto> boletos = new ArrayList<>();

        String[] modelosAvion = {
            "Boeing 737","Boeing 747","Boeing 757","Boeing 767","Boeing 777",
            "Boeing 787 Dreamliner","Airbus A318","Airbus A319","Airbus A320",
            "Airbus A321","Airbus A330","Airbus A340","Airbus A350","Airbus A380",
            "Embraer E170","Embraer E175","Embraer E190","Embraer E195",
            "Bombardier CRJ700","Bombardier CRJ900","ATR 42","ATR 72",
            "McDonnell Douglas DC-10","McDonnell Douglas MD-11","Concorde"
        };

        String[] paises = {
            "Estados Unidos","España","México","Panamá","Colombia",
            "Perú","Brasil","Argentina","Chile","Canadá",
            "Cuba","Puerto Rico","Venezuela","Francia","Alemania",
            "Italia","Portugal","Reino Unido","Holanda","Bélgica",
            "Japón","China","Corea del Sur","Australia","Nueva Zelanda",
            "India","Emiratos Árabes Unidos","Arabia Saudita","Sudáfrica","Egipto"
        };

        String[] aerolineas = {
            "AeroMexico","Air Antilles Express","Air Caraibes","Air Century",
            "Air Europa","American Airlines","Arajet","Avianca","Avior Airlines",
            "Condor","Copa Airlines","Delta Airlines","Eastern Airlines",
            "Frontier Airlines","GlobalX Airlines","Iberia","Intercaribbean",
            "Jet Blue","Laser Airlines","Rutaca","Sky Cana","Sky High Aviation",
            "Spirit Airlines","Sunrise","Turpial Airlines","United Airlines",
            "Venezolana","Wingo","World2Fly"
        };

        HashMap<String, String> codigos = new HashMap<>();
        codigos.put("Estados Unidos", "JFK"); codigos.put("España", "MAD");
        codigos.put("México", "MEX"); codigos.put("Panamá", "PTY");
        codigos.put("Colombia", "BOG"); codigos.put("Perú", "LIM");
        codigos.put("Brasil", "GRU"); codigos.put("Argentina", "EZE");
        codigos.put("Chile", "SCL"); codigos.put("Canadá", "YYZ");
        codigos.put("Cuba", "HAV"); codigos.put("Puerto Rico", "SJU");
        codigos.put("Venezuela", "CCS"); codigos.put("Francia", "CDG");
        codigos.put("Alemania", "FRA"); codigos.put("Italia", "FCO");
        codigos.put("Portugal", "LIS"); codigos.put("Reino Unido", "LHR");
        codigos.put("Holanda", "AMS"); codigos.put("Bélgica", "BRU");
        codigos.put("Japón", "NRT"); codigos.put("China", "PEK");
        codigos.put("Corea del Sur", "ICN"); codigos.put("Australia", "SYD");
        codigos.put("Nueva Zelanda", "AKL"); codigos.put("India", "DEL");
        codigos.put("Emiratos Árabes Unidos", "DXB"); codigos.put("Arabia Saudita", "RUH");
        codigos.put("Sudáfrica", "JNB"); codigos.put("Egipto", "CAI");

        int opcion;
        do {
            System.out.println("\n===== SISTEMA AEROPUERTO =====");
            System.out.println("1. Registrar avión");
            System.out.println("2. Registrar vuelo");
            System.out.println("3. Comprar boleto");
            System.out.println("4. Mostrar vuelos");
            System.out.println("5. Mostrar aviones");
            System.out.println("6. Ver boletos");
            System.out.println("7. Salidas");
            System.out.println("8. Llegadas");
            System.out.println("9. Panel aeropuerto 🛫");
            System.out.println("10. Pasajeros por vuelo 🧍");
            System.out.println("11. Reporte aeropuerto");
            System.out.println("12. Salir");

            opcion = sc.nextInt();
            sc.nextLine();

            switch(opcion){
                case 1:
                    String codigo = "AV" + (100 + random.nextInt(900));
                    String modelo = modelosAvion[random.nextInt(modelosAvion.length)];
                    int capacidad = 120 + random.nextInt(200);
                    aviones.add(new Avion(codigo, modelo, capacidad));
                    System.out.println("Avión registrado: " + codigo + " " + modelo);
                    break;

                case 2:
                    if (aviones.isEmpty()) { System.out.println("Registre aviones primero"); break; }
                    String numVuelo = generarNumeroVuelo();
                    System.out.println("Seleccione destino:");
                    for (int i=0; i<paises.length; i++) System.out.println((i+1)+". "+paises[i]);
                    int d = sc.nextInt(); sc.nextLine();
                    System.out.println("Seleccione aerolínea:");
                    for (int i=0; i<aerolineas.length; i++) System.out.println((i+1)+". "+aerolineas[i]);
                    int a = sc.nextInt(); sc.nextLine();
                    System.out.println("1. Salida\n2. Llegada");
                    int t = sc.nextInt(); sc.nextLine();
                    String tipo = (t==1) ? "SALIDA" : "LLEGADA";
                    Avion avionAsignado = aviones.get(random.nextInt(aviones.size()));
                    vuelos.add(new Vuelo(numVuelo, paises[d-1], aerolineas[a-1], tipo, avionAsignado));
                    System.out.println("Vuelo registrado con avión " + avionAsignado.modelo + " | Número de vuelo: " + numVuelo);
                    break;

                case 3:
                    if (vuelos.isEmpty()) { System.out.println("No hay vuelos disponibles"); break; }
                    System.out.println("Seleccione destino:");
                    for (int i=0; i<paises.length; i++) System.out.println((i+1)+". "+paises[i]);
                    int des = sc.nextInt(); sc.nextLine();
                    System.out.println("Seleccione aerolínea:");
                    for (int i=0; i<aerolineas.length; i++) System.out.println((i+1)+". "+aerolineas[i]);
                    int aer = sc.nextInt(); sc.nextLine();
                    System.out.println("Clase:\n1. Primera ($1000)\n2. Segunda ($700)\n3. Tercera ($400)");
                    int cl = sc.nextInt(); sc.nextLine();
                    double precio = (cl==1)?1000:(cl==2)?700:400;
                    String clase = (cl==1)?"Primera":(cl==2)?"Segunda":"Tercera";
                    System.out.println("Tipo de viaje:\n1. Ida\n2. Ida y vuelta");
                    int viaje = sc.nextInt(); sc.nextLine();
                    String tipoViaje = (viaje==2)?"Ida y vuelta":"Ida";
                    if (viaje==2) precio*=2;

                    Vuelo vueloSeleccionado = vuelos.get(random.nextInt(vuelos.size()));
                    String asiento = generarAsiento(vueloSeleccionado.avion.codigo);
                    String boletoNum = generarNumeroBoleto();
                    String fecha = generarFecha();
                    String hora = generarHora();
                    String gate = generarGate();
                    String destinoNombre = paises[des-1];
                    String destinoCod = codigos.get(destinoNombre);
                    String pasajeroNombre = generarNombrePasajero();

                    Boleto b = new Boleto(boletoNum, pasajeroNombre, aerolineas[aer-1],
                            "SANTO DOMINGO", destinoNombre, "SDQ", destinoCod,
                            clase, tipoViaje, asiento, fecha, hora, gate, precio);
                    boletos.add(b);
                    b.imprimir();
                    break;

                case 4:
                    System.out.printf("%-8s %-20s %-25s %-10s %-30s\n", "Num", "Destino", "Aerolínea", "Tipo", "Avión");
                    for (Vuelo v:vuelos) v.mostrar();
                    break;

                case 5:
                    System.out.printf("%-10s %-30s %-10s\n", "Código", "Modelo", "Capacidad");
                    for (Avion av:aviones) av.mostrar();
                    break;

                case 6:
                    for (Boleto bol:boletos) bol.imprimir();
                    break;

                case 7:
                    System.out.println("===== SALIDAS =====");
                    for (Vuelo v:vuelos) if (v.tipo.equals("SALIDA")) v.mostrar();
                    break;

                case 8:
                    System.out.println("===== LLEGADAS =====");
                    for (Vuelo v:vuelos) if (v.tipo.equals("LLEGADA")) v.mostrar();
                    break;

                case 9:
                    System.out.println("\n===== PANEL AEROPUERTO 🛫 =====");
                    System.out.printf("%-10s %-8s %-20s %-20s %-10s %-30s %-10s\n",
                            "Vuelo", "Tipo", "Origen", "Destino", "Gate", "Avión", "Hora");
                    for (Vuelo v : vuelos) {
                        System.out.printf("%-10s %-8s %-20s %-20s %-10s %-30s %-10s\n",
                                v.numero, v.tipo, "SDQ", v.destino, generarGate(), v.avion.modelo, generarHora());
                    }
                    break;

                case 10:
                    System.out.println("\n===== PASAJEROS POR VUELO 🧍 =====");
                    for (Vuelo v : vuelos) {
                        System.out.println("\nVuelo: " + v.numero + " Destino: " + v.destino + " Avión: " + v.avion.modelo);
                        for (Boleto bol : boletos) {
                            if (bol.destino.equals(v.destino)) {
                                System.out.printf("Pasajero: %-20s Asiento: %-6s Clase: %-10s\n",
                                        bol.pasajero, bol.asiento, bol.clase);
                            }
                        }
                    }
                    break;

                case 11:
                    int salidas = 0, llegadas = 0;
                    for (Vuelo v:vuelos) {
                        if (v.tipo.equals("SALIDA")) salidas++;
                        else if (v.tipo.equals("LLEGADA")) llegadas++;
                    }
                    System.out.println("\n================ REPORTE DEL AEROPUERTO ================");
                    System.out.println("Total de aviones: " + aviones.size());
                    System.out.println("Total de vuelos: " + vuelos.size());
                    System.out.println("Total de boletos vendidos: " + boletos.size());
                    System.out.println("Total salidas: " + salidas);
                    System.out.println("Total llegadas: " + llegadas);
                    System.out.println("========================================================");
                    break;

                case 12:
                    System.out.println("Sistema cerrado");
                    break;

                default:
                    System.out.println("Opción no válida");
            }

        } while(opcion != 12);

        sc.close();
    }
}