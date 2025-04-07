package hu.techbazaar;

public class Home_items {
    private String name, desc, price;
    private int imgsrc;
    private float rate;
    private boolean isFavorite;

    public Home_items(String name, String desc, String price, int imgsrc, float rate) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.imgsrc = imgsrc;
        this.rate = rate;
        this.isFavorite = false;
    }

    public String getName() {return name;}
    public String getDesc() {return desc;}
    public String getPrice() {return price;}
    public float getRate() {return rate;}
    public int getImgsrc() {return imgsrc;}
    public boolean isFavorite() {return isFavorite;}
    public void setFavorite(boolean favorite) {isFavorite = favorite;}
}
