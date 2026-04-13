package org.firstinspires.ftc.teamcode;

public enum Color {
    WHITE,
    GREEN,
    RED,
    BLUE,
    ORANGE,
    YELLOW;

    public String getI() {
        return this.name().substring(0, 1).equals("W") ? "U" : this.name().substring(0, 1);
    }
    public String getS() {
        if(this.name() == "WHITE")
            return "U";
        else if(this.name() == "ORANGE")
            return "L";
        else if(this.name() == "GREEN")
            return "F";
        else if(this.name() == "RED")
            return "R";
        else if(this.name() == "BLUE")
            return "B";
        else if(this.name() == "YELLOW")
            return "D";
        else
            return"?";
    }
}
