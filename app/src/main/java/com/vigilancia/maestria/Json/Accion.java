package com.vigilancia.maestria.Json;

import java.util.ArrayList;

public class Accion{
    private ArrayList<Command> commands;

    public Accion(ArrayList<Command> commands) {
        this.commands = commands;
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public void setCommands(ArrayList<Command> commands) {
        this.commands = commands;
    }
}
