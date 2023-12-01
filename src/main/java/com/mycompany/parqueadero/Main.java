package com.mycompany.parqueadero;

import static spark.Spark.*;
import com.google.gson.Gson;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        
        Gson gson = new Gson();
        GestorVehiculo gestor = new GestorVehiculo();
        
        LinkedList<Automovil> automoviles = gestor.cargarDesdeArchivo("autos.ser");
        LinkedList<Motocicleta> motos = gestor.cargarDesdeArchivo("motos.ser");
        
         Automovil auto = new Automovil(4, "Mazda", "3", "ZYX987", 8, 0);
        automoviles.add(auto);
        
        Motocicleta moto = new Motocicleta(600, "Honda", "CBR600", "XYZ789", 5, 0);
        motos.add(moto);
        
         get("/motos", (req, res) -> {
            res.type("application/json");
            return gson.toJson(motos);
        });
        
        get("/automoviles", (req, res) -> {
            res.type("application/json");
            return gson.toJson(automoviles);
        });
        
         get("/agregarAutomovil/:marca/:modelo/:placa/:numeroPuertas/:horaIngreso/:horaSalida", (req, res) -> {
            
             res.type("application/json");

            String marca = req.params(":marca");
            String modelo = req.params(":modelo");
            String placa = req.params(":placa");
            
            int numeroPuertas = Integer.parseInt(req.params(":numeroPuertas"));
            int horaIngreso = Integer.parseInt(req.params(":horaIngreso"));
            int horaSalida = Integer.parseInt(req.params(":horaSalida"));

            Automovil nuevoAuto = new Automovil(numeroPuertas, marca, modelo,placa, horaIngreso, horaSalida);
            automoviles.add(nuevoAuto);
            gestor.guardarEnArchivo(automoviles, "autos.ser");

            return gson.toJson(nuevoAuto);
        });
         
           get("/agregarMoto/:marca/:modelo/:placa/:cilindrada/:horaIngreso/:horaSalida", (req, res) -> {
            
            res.type("application/json");

            String marca = req.params(":marca");
            String modelo = req.params(":modelo");
            String placa = req.params(":placa");
            
            int cilindrada = Integer.parseInt(req.params(":cilindrada"));
            int horaIngreso = Integer.parseInt(req.params(":horaIngreso"));
            int horaSalida = Integer.parseInt(req.params(":horaSalida"));
          
            Motocicleta nuevoMoto = new Motocicleta(cilindrada, marca, modelo,placa, horaIngreso, horaSalida);
            motos.add(nuevoMoto);
            gestor.guardarEnArchivo(motos, "motos.ser");
             
            return gson.toJson(nuevoMoto);
        });
        
         get("/sacarAutomovil/:placa/:horaSalida", (req, res)->{
            
            res.type("application/json");
            
            String placa = req.params(":placa");
            int horaSalida = Integer.parseInt(req.params(":horaSalida"));
                        
            for(Automovil e: automoviles){
                if(e.getPlaca().equalsIgnoreCase(placa)){
                     e.setHoraSalida(horaSalida);
            return gson.toJson("El automovil de la placa "+ placa+ " salio del parqueadero a la hora: "+ horaSalida);
                }else{
            return gson.toJson("la placa ingresada no existe en el parqueadero");
                }
            }
            return gson.toJson( placa);
        });
         
          get("/sacarMotocicleta/:placa/:horaSalida", (req, res)->{
            
            res.type("application/json");
            
            String placa = req.params(":placa");
            int horaSalida = Integer.parseInt(req.params(":horaSalida"));
         
            for(Motocicleta e: motos){
                if(e.getPlaca().equalsIgnoreCase(placa)){
                    e.setHoraSalida(horaSalida);
                return gson.toJson("La motocicleta de la placa "+ placa+ " salio del parqueadero a la hora: "+ horaSalida);

                }else{
                    return gson.toJson("la placa ingresada no existe en el parqueadero");
                }
            }
            return gson.toJson(placa);
        });
          
           get("/motosActuales/:placa", (req, res)->{
            
            res.type("application/json");
            
            String placa = req.params(":placa");
         
            for(Motocicleta e: motos){
                if(e.getPlaca().equalsIgnoreCase(placa)){ 
                return gson.toJson("La motocicleta de la placa ingresada se encuentra en el parqueadero");
                }else{
                    return gson.toJson("la placa ingresada no existe en el parqueadero");
                }
            }
            return gson.toJson(placa);
        });
           
            get("/automovilesActuales/:placa", (req, res)->{
            
            res.type("application/json");
            
            String placa = req.params(":placa");
         
            for(Automovil e: automoviles){
                if(e.getPlaca().equalsIgnoreCase(placa)){ 
                return gson.toJson("El automovil de la placa ingresada se encuentra en el parqueadero");
                }else{
                    return gson.toJson("la placa ingresada no existe en el parqueadero");
                }
            }
            return gson.toJson(placa);
        });
            
             get("/automovilesReporte", (req, res)->{
            
            res.type("application/json");
            
            int horas;
            int ganancias=0;
            
            for(Automovil e: automoviles){
                horas = e.getHoraSalida()-e.getHoraIngreso();
                ganancias += (horas*5000);
            }
            
            return gson.toJson(ganancias);   
        });
             
              get("/motosReporte", (req, res)->{
            
            res.type("application/json");

            int horas;
            int ganancias=0;

            for(Motocicleta e: motos){
                if(e!=null){
                    horas = e.getHoraSalida()-e.getHoraIngreso();
                    ganancias += horas*5000;
                }
            }
            
            System.out.println("El total de ganancias de las motos es: "+ ganancias);
            return gson.toJson(ganancias);   
        });   
        
    }
}
