package lk.ijse.dep.pharmacy.entity;

public class Stock implements SuperEntity {

    private StockPK stockPK;
    private int drugQty;
    private double drugUnitPrice;

    public Stock() {
    }

    public Stock(StockPK stockPK, int drugQty, double drugUnitPrice) {
        this.stockPK = stockPK;
        this.drugQty = drugQty;
        this.drugUnitPrice = drugUnitPrice;
    }

    public Stock(String batchId,String drugCode, int drugQty, double drugUnitPrice) {
        this.stockPK = new StockPK(batchId,drugCode);
        this.drugQty = drugQty;
        this.drugUnitPrice = drugUnitPrice;
    }

    public StockPK getStockPK() {
        return stockPK;
    }

    public void setStockPK(StockPK stockPK) {
        this.stockPK = stockPK;
    }

    public int getDrugQty() {
        return drugQty;
    }

    public void setDrugQty(int drugQty) {
        this.drugQty = drugQty;
    }

    public double getDrugUnitPrice() {
        return drugUnitPrice;
    }

    public void setDrugUnitPrice(double drugUnitPrice) {
        this.drugUnitPrice = drugUnitPrice;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockPK=" + stockPK +
                ", drugQty=" + drugQty +
                ", drugUnitPrice=" + drugUnitPrice +
                '}';
    }
}
