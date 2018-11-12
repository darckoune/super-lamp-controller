package com.example.darckoune.futuregadgetlamp;

import java.util.ArrayList;

public class ZonesSingleton {
    ArrayList<Zone> zones = new ArrayList<Zone>();

    String gatewayName;

    private ZonesSingleton() {
    }

    public void clearZones (){
        this.zones.clear();
    }

    public void addZone(Zone z){
        this.zones.add(z);
    }

    public ArrayList<Zone> getZones() {
        return zones;
    }

    public String getGatewayName() {
        return gatewayName;
    }

    public void setGatewayName(String gatewayName) {
        this.gatewayName = gatewayName;
    }



    /** Instance unique non préinitialisée */
    private static ZonesSingleton INSTANCE = null;

    /** Point d'accès pour l'instance unique du singleton */
    public static synchronized ZonesSingleton getInstance()
    {
        if (INSTANCE == null)
        {   INSTANCE = new ZonesSingleton();
        }
        return INSTANCE;
    }
}
