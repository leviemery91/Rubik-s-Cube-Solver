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
        switch (turn) {
            case "U'": {
                Color[] o = {cube.clone()[4][0][0], cube.clone()[4][0][1], cube.clone()[4][0][2]};
                cube[4][0] = cube.clone()[3][0];
                cube[3][0] = cube.clone()[2][0];
                cube[2][0] = cube.clone()[1][0];
                cube[1][0] = o;
                cube[0] = spinFace(cube.clone()[0], true);
                break;
            }
            case "U": {
                Color[] o = {cube.clone()[4][0][0], cube.clone()[4][0][1], cube.clone()[4][0][2]};
                cube[4][0] = cube.clone()[1][0];
                cube[1][0] = cube.clone()[2][0];
                cube[2][0] = cube.clone()[3][0];
                cube[3][0] = o;
                cube[0] = spinFace(cube.clone()[0], false);
                break;
            }
            case "D": {
                Color[] tempRow = {cube.clone()[1][2][0], cube.clone()[1][2][1], cube.clone()[1][2][2]};
                cube[1][2] = cube.clone()[4][2];
                cube[4][2] = cube.clone()[3][2];
                cube[3][2] = cube.clone()[2][2];
                cube[2][2] = tempRow;
                cube[5] = spinFace(cube.clone()[5], false);
                break;
            }
            case "D'": {
                Color[] tempRow = {cube.clone()[1][2][0], cube.clone()[1][2][1], cube.clone()[1][2][2]};
                cube[1][2] = cube.clone()[2][2];
                cube[2][2] = cube.clone()[3][2];
                cube[3][2] = cube.clone()[4][2];
                cube[4][2] = tempRow;
                cube[5] = spinFace(cube.clone()[5], true);
                break;
            }
            case "R": {
                Color[] tempColumn = {cube.clone()[0][0][2], cube.clone()[0][1][2], cube.clone()[0][2][2]};

                cube[0][0][2] = cube.clone()[1][0][2];
                cube[0][1][2] = cube.clone()[1][1][2];
                cube[0][2][2] = cube.clone()[1][2][2];

                cube[1][0][2] = cube.clone()[5][0][2];
                cube[1][1][2] = cube.clone()[5][1][2];
                cube[1][2][2] = cube.clone()[5][2][2];

                cube[5][0][2] = cube.clone()[3][2][0];
                cube[5][1][2] = cube.clone()[3][1][0];
                cube[5][2][2] = cube.clone()[3][0][0];

                cube[3][0][0] = tempColumn[2];
                cube[3][1][0] = tempColumn[1];
                cube[3][2][0] = tempColumn[0];
                cube[2] = spinFace(cube.clone()[2], false);
                break;
            }
            case "R'": {
                Color[] tempColumn = {cube.clone()[0][0][2], cube.clone()[0][1][2], cube.clone()[0][2][2]};

                cube[0][0][2] = cube.clone()[3][2][0];
                cube[0][1][2] = cube.clone()[3][1][0];
                cube[0][2][2] = cube.clone()[3][0][0];

                cube[3][0][0] = cube.clone()[5][2][2];
                cube[3][1][0] = cube.clone()[5][1][2];
                cube[3][2][0] = cube.clone()[5][0][2];

                cube[5][0][2] = cube.clone()[1][0][2];
                cube[5][1][2] = cube.clone()[1][1][2];
                cube[5][2][2] = cube.clone()[1][2][2];

                cube[1][0][2] = tempColumn[0];
                cube[1][1][2] = tempColumn[1];
                cube[1][2][2] = tempColumn[2];

                cube[2] = spinFace(cube.clone()[2], true);
                break;
            }
            case "L": {
                Color[] tempColumn = {cube.clone()[0][0][0], cube.clone()[0][1][0], cube.clone()[0][2][0]};

                cube[0][0][0] = cube.clone()[3][2][2];
                cube[0][1][0] = cube.clone()[3][1][2];
                cube[0][2][0] = cube.clone()[3][0][2];

                cube[3][2][2] = cube.clone()[5][0][0];
                cube[3][1][2] = cube.clone()[5][1][0];
                cube[3][0][2] = cube.clone()[5][2][0];

                cube[5][0][0] = cube.clone()[1][0][0];
                cube[5][1][0] = cube.clone()[1][1][0];
                cube[5][2][0] = cube.clone()[1][2][0];

                cube[1][0][0] = tempColumn[0];
                cube[1][1][0] = tempColumn[1];
                cube[1][2][0] = tempColumn[2];

                cube[4] = spinFace(cube.clone()[4], false);
                break;
            }
            case "L'": {
                Color[] tempColumn = {cube.clone()[0][0][0], cube.clone()[0][1][0], cube.clone()[0][2][0]};

                cube[0][0][0] = cube.clone()[1][0][0];
                cube[0][1][0] = cube.clone()[1][1][0];
                cube[0][2][0] = cube.clone()[1][2][0];

                cube[1][0][0] = cube.clone()[5][0][0];
                cube[1][1][0] = cube.clone()[5][1][0];
                cube[1][2][0] = cube.clone()[5][2][0];

                cube[5][0][0] = cube.clone()[3][2][2];
                cube[5][1][0] = cube.clone()[3][1][2];
                cube[5][2][0] = cube.clone()[3][0][2];

                cube[3][2][2] = tempColumn[0];
                cube[3][1][2] = tempColumn[1];
                cube[3][0][2] = tempColumn[2];

                cube[4] = spinFace(cube.clone()[4], true);
                break;
            }
            case "F": {
                Color[] tempRow = {cube.clone()[0][2][0], cube.clone()[0][2][1], cube.clone()[0][2][2]};

                cube[0][2][0] = cube.clone()[4][2][2];
                cube[0][2][1] = cube.clone()[4][1][2];
                cube[0][2][2] = cube.clone()[4][0][2];

                cube[4][2][2] = cube.clone()[5][0][2];
                cube[4][1][2] = cube.clone()[5][0][1];
                cube[4][0][2] = cube.clone()[5][0][0];

                cube[5][0][0] = cube.clone()[2][2][0];
                cube[5][0][1] = cube.clone()[2][1][0];
                cube[5][0][2] = cube.clone()[2][0][0];

                cube[2][2][0] = tempRow[2];
                cube[2][1][0] = tempRow[1];
                cube[2][0][0] = tempRow[0];

                cube[1] = spinFace(cube.clone()[1], false);
                break;
            }
            case "F'": {
                Color[] tempRow = {cube.clone()[0][2][0], cube.clone()[0][2][1], cube.clone()[0][2][2]};

                cube[0][2][0] = cube.clone()[2][0][0];
                cube[0][2][1] = cube.clone()[2][1][0];
                cube[0][2][2] = cube.clone()[2][2][0];

                cube[2][0][0] = cube.clone()[5][0][2];
                cube[2][1][0] = cube.clone()[5][0][1];
                cube[2][2][0] = cube.clone()[5][0][0];

                cube[5][0][2] = cube.clone()[4][2][2];
                cube[5][0][1] = cube.clone()[4][1][2];
                cube[5][0][0] = cube.clone()[4][0][2];

                cube[4][2][2] = tempRow[0];
                cube[4][1][2] = tempRow[1];
                cube[4][0][2] = tempRow[2];

                cube[1] = spinFace(cube.clone()[1], true);
                break;
            }
            case "B": {
                Color[] tempRow = {cube.clone()[0][0][0], cube.clone()[0][0][1], cube.clone()[0][0][2]};

				cube[0][0][0] = cube.clone()[2][0][2];
				cube[0][0][1] = cube.clone()[2][1][2];
				cube[0][0][2] = cube.clone()[2][2][2];

				cube[2][0][2] = cube.clone()[5][2][2];
				cube[2][1][2] = cube.clone()[5][2][1];
				cube[2][2][2] = cube.clone()[5][2][0];

				cube[5][2][2] = cube.clone()[4][2][0];
				cube[5][2][1] = cube.clone()[4][1][0];
				cube[5][2][0] = cube.clone()[4][0][0];

				cube[4][2][0] = tempRow[0];
				cube[4][1][0] = tempRow[1];
				cube[4][0][0] = tempRow[2];
	
                cube[3] = spinFace(cube.clone()[3], false);
                break;
            }
            case "B'": {
                Color[] tempRow = {cube.clone()[0][0][0], cube.clone()[0][0][1], cube.clone()[0][0][2]};

				cube[0][0][0] = cube.clone()[4][2][0];
				cube[0][0][1] = cube.clone()[4][1][0];
				cube[0][0][2] = cube.clone()[4][0][0];

                cube[4][0][0] = cube.clone()[5][2][0];
                cube[4][1][0] = cube.clone()[5][2][1];
                cube[4][2][0] = cube.clone()[5][2][2];

                cube[5][2][0] = cube.clone()[2][2][2];
                cube[5][2][1] = cube.clone()[2][1][2];
                cube[5][2][2] = cube.clone()[2][0][2];

				cube[2][2][2] = tempRow[2];
				cube[2][1][2] = tempRow[1];
				cube[2][0][2] = tempRow[0];

                cube[3] = spinFace(cube.clone()[3], true);
                break;
            }
        }


    }
    public Color[][] spinFace(Color[][] side, boolean prime){
        Color tempCorner = side.clone()[0][0];
        Color tempEdge = side.clone()[0][1];
        if(prime){
            side[0][0] = side.clone()[0][2];
            side[0][2] = side.clone()[2][2];
            side[2][2] = side.clone()[2][0];
            side[2][0] = tempCorner;

            side[0][1] = side.clone()[1][2];
            side[1][2] = side.clone()[2][1];
            side[2][1] = side.clone()[1][0];
            side[1][0] = tempEdge;

        }
        else{
            side[0][0] = side.clone()[2][0];
            side[2][0] = side.clone()[2][2];
            side[2][2] = side.clone()[0][2];
            side[0][2] = tempCorner;

            side[0][1] = side.clone()[1][0];
            side[1][0] = side.clone()[2][1];
            side[2][1] = side.clone()[1][2];
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

    public String convertToFacelet(){
        String facelet = "";
        int[] order = {0,2,1,5,4,3}; //urfdlb
        for(int i = 0; i < 6;i++){
            for(int j = 0; j < 3; j++){
                for(int k = 0; k < 3; k++){
                    facelet = facelet + cube[order[i]][j][k].getS();
                }
            }
        }
        return facelet;
    }

    public Color[][][] returnCube(){
        return cube;
    }
}
