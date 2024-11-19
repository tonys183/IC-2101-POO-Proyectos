package Tiles;

import Objects.Cupones.Cupon;
import Objects.Cupones.cBombaDorada;
import main.AssetSetter;
import main.Bomberman;
import main.Dupla;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.CollationElementIterator;
import java.util.*;


public class TileManager {
    private Bomberman bomberman;
    private Tile[] tile;
    private int mapTileNum[][];
    private int[][] hiddenItems;
    private ArrayList<Dupla<Integer,Integer>> valsHidden;

    public TileManager(Bomberman bomberman) {
        this.tile = new Tile[20];
        this.bomberman = bomberman;
        this.mapTileNum = new int[17][15];
        this.hiddenItems = new int[17][15];
        this.valsHidden = new ArrayList<>();
        getTileImage();
        loadMap();
        loadBricks();
    }

    public void reset(){
        this.tile = new Tile[20];
        this.mapTileNum = new int[17][15];
        this.hiddenItems = new int[17][15];
        this.valsHidden.clear();
        getTileImage();
        loadMap();
        loadBricks();
    }

    public int[][] getMapTileNum() { return mapTileNum; }

    public Tile[] getTile() { return tile; }

    public Tile getTile(int x) { return tile[x]; }

    public int[][] getHiddenItems() { return hiddenItems; }

    public void setTileImage(String path, int x){
        try{
            tile[x].setImage(ImageIO.read(getClass().getResourceAsStream(path)));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void getTileImage() {
        try{
            if(bomberman.getLevelActu() != 0 && bomberman.getLevelActu()%5 == 0){
                tile[0]=new Tile();
                tile[0].setImage(ImageIO.read(getClass().getResourceAsStream("/Sprites/Tile/ExtraGround.png")));

                tile[1]=new Tile();
                tile[1].setImage(ImageIO.read(getClass().getResourceAsStream("/Sprites/Tile/ExtraBlock.png")));
                tile[1].setCollision(true);
            }
            else{
                tile[0]=new Tile();
                tile[0].setImage(ImageIO.read(getClass().getResourceAsStream("/Sprites/Tile/Ground.png")));

                tile[1]=new Tile();
                tile[1].setImage(ImageIO.read(getClass().getResourceAsStream("/Sprites/Tile/Block.png")));
                tile[1].setCollision(true);
            }

            tile[2]=new Tile();
            tile[2].setImage(ImageIO.read(getClass().getResourceAsStream("/Sprites/Tile/Brick.png")));
            tile[2].setCollision(true);

            tile[3]=new Tile();
            tile[3].setImage(ImageIO.read(getClass().getResourceAsStream("/Sprites/Tile/Door.png")));

            tile[4]=new Tile();
            tile[4].setImage(ImageIO.read(getClass().getResourceAsStream("/Sprites/Cupones/sol.png")));

            tile[5]=new Tile();
            tile[5].setImage(ImageIO.read(getClass().getResourceAsStream("/Sprites/Cupones/bombaD.png")));

            tile[6]=new Tile();
            tile[6].setImage(ImageIO.read(getClass().getResourceAsStream("/Sprites/Cupones/deto.png")));

            tile[7]=new Tile();
            tile[7].setImage(ImageIO.read(getClass().getResourceAsStream("/Sprites/Cupones/patin.png")));

            tile[8]=new Tile();
            tile[8].setImage(ImageIO.read(getClass().getResourceAsStream("/Sprites/Cupones/bombaRayada.png")));

            tile[9]=new Tile();
            tile[9].setImage(ImageIO.read(getClass().getResourceAsStream("/Sprites/Cupones/muroRayado.png")));

            tile[10]=new Tile();
            tile[10].setImage(ImageIO.read(getClass().getResourceAsStream("/Sprites/Cupones/pregunta.png")));

            tile[11]=new Tile();
            tile[11].setImage(ImageIO.read(getClass().getResourceAsStream("/Sprites/Cupones/hombreLlamas.png")));

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap() {
        try {
            InputStream is= getClass().getResourceAsStream("/Resources/Tablero.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0, row = 0;
            while (col < bomberman.getSizeY() && row < bomberman.getSizeX()) {
                String line = br.readLine();

                while(col < bomberman.getSizeY()) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == bomberman.getSizeY()) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadBricks(){
        Set<Integer> bricks = new HashSet<>(Arrays.asList(18,19,35));
        ArrayList<Dupla<Integer,Integer>> valBricks = new ArrayList<>();
        try {
            InputStream is= getClass().getResourceAsStream("/Resources/Tablero.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0, row = 0;
            while (col < bomberman.getSizeY() && row < bomberman.getSizeX()) {
                String line = br.readLine();

                while(col < bomberman.getSizeY()) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    if(num==0 && !bricks.contains(row*17+col)) {
                        valBricks.add(new Dupla(col,row));
                    }
                    col++;
                }
                if(col == bomberman.getSizeY()) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        Collections.shuffle(valBricks);
        Random random = new Random();
        int brickCount = random.nextInt(80) + 2;
        while (brickCount != 0 && !valBricks.isEmpty()) {
            Dupla<Integer, Integer> brickPosition = valBricks.remove(0);
            mapTileNum[brickPosition.getPrimero()][brickPosition.getSegundo()] = 2;
            valsHidden.add(new Dupla<>(brickPosition.getPrimero(),brickPosition.getSegundo()));
            hiddenItems[brickPosition.getPrimero()][brickPosition.getSegundo()]  = 0;
            brickCount--;
        }
    }

    public void assignHiddenItems() {
        Collections.shuffle(valsHidden);
        int nivelActu = bomberman.getLevelActu();

        if (!valsHidden.isEmpty() && (nivelActu%5)!=0) {
            Dupla<Integer, Integer> doorPosition = valsHidden.remove(0);
            mapTileNum[doorPosition.getPrimero()][doorPosition.getSegundo()] = 2;
            hiddenItems[doorPosition.getPrimero()][doorPosition.getSegundo()] = 3;
            //System.out.println(doorPosition.getPrimero()+" "+doorPosition.getSegundo());
            Dupla<Cupon,Integer> cuponDorado = bomberman.getCupon();
            Dupla<Integer,Integer> cuponPos = valsHidden.remove(0);
            mapTileNum[cuponPos.getPrimero()][cuponPos.getSegundo()] = 2;
            hiddenItems[cuponPos.getPrimero()][cuponPos.getSegundo()] = cuponDorado.getSegundo();
            //System.out.println(cuponPos.getPrimero()+" "+cuponPos.getSegundo());
            AssetSetter setter = bomberman.getAssetSetter();
            setter.setCupon(cuponPos.getPrimero(),cuponPos.getSegundo(),cuponDorado.getPrimero());
        }
    }

    public void draw(Graphics2D g2) {
        int tileSize = bomberman.getCellSize();
        int tableroWidth = bomberman.getSizeY() * tileSize;
        int tableroHeight = bomberman.getSizeX() * tileSize;

        int startX = (bomberman.getWidth() - tableroWidth) / 2;
        int startY = (bomberman.getHeight() - tableroHeight) / 2;

        int col = 0, row = 0;
        int x = startX;
        int y = startY;

        while (col < bomberman.getSizeY() && row < bomberman.getSizeX()){
            int tileNum = mapTileNum[col][row];
            g2.drawImage(tile[tileNum].getImage(), x, y, tileSize, tileSize, null);
            col++;
            x += tileSize;
            if (col == bomberman.getSizeY()) {
                col = 0;
                x = startX;
                row++;
                y += tileSize;
            }
        }
    }
}
