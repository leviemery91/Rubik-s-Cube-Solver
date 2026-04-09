package org.firstinspires.ftc.teamcode;

public class Cube {
    Color[][][] cube = new Color[6][3][3];

    public Cube(){
        Color[] colors = {Color.WHITE, Color.GREEN, Color.RED, Color.BLUE, Color.ORANGE, Color.YELLOW};
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 3; j++){
                for(int k = 0; k < 3; k++){
                    cube[i][j][k] = colors[i];
                }
            }
        }
    }

    public void updateCube(String turn){
        if(turn.equals("U'")){
            Color[] o = cube[4][0];
            cube[4][0] = cube[3][0];
            cube[3][0] = cube[2][0];
            cube[2][0] = cube[1][0];
            cube[1][0] = o;
            cube[0] = spinFace(cube[0], true);
        }
        else if(turn.equals("U")){
            Color[] o = cube[4][0];
            cube[4][0] = cube[1][0];
            cube[1][0] = cube[2][0];
            cube[2][0] = cube[3][0];
            cube[3][0] = o;
            cube[0] = spinFace(cube[0], false);
        }
        else if(turn.equals("D")){}
        else if(turn.equals("D'")){}
        else if(turn.equals("R")){
            cube[2] = spinFace(cube[2],false);
        }
        else if(turn.equals("R'")){
            cube[2] = spinFace(cube[2],true);
        }
        else if(turn.equals("L")){}
        else if(turn.equals("L'")){}
        else if(turn.equals("F")){}
        else if(turn.equals("F'")){}
        else if(turn.equals("B")){}
        else if(turn.equals("B'")){}


    }
    public Color[][] spinFace(Color[][] side, boolean prime){
        Color tempCorner = side[0][0];
        Color tempEdge = side[0][1];
        if(prime){
            side[0][0] = side[0][2];
            side[0][2] = side[2][2];
            side[2][2] = side[0][2];
            side[0][2] = tempCorner;

            side[0][1] = side[1][2];
            side[1][2] = side[2][1];
            side[2][1] = side[1][0];
            side[1][0] = tempEdge;

        }
        else{
            side[0][0] = side[2][0];
            side[2][0] = side[2][2];
            side[2][2] = side[0][2];
            side[0][2] = tempCorner;

            side[0][1] = side[1][0];
            side[1][0] = side[2][1];
            side[2][1] = side[1][2];
            side[1][2] = tempEdge;
        }
        return side;
    }

    public boolean cubeSolved(){
        for(Color[][] side : cube){
            Color center = side[1][1];
            for(Color[] row: side){
                for(Color c : row){
                    if(c!=center){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void resetCube(){
        Color[] colors = {Color.WHITE, Color.GREEN, Color.RED, Color.BLUE, Color.ORANGE, Color.YELLOW};
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 3; j++){
                for(int k = 0; k < 3; k++){
                    cube[i][j][k] = colors[i];
                }
            }
        }
    }
}
