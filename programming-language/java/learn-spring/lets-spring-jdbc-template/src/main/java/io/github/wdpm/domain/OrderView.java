package io.github.wdpm.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * @author evan
 * @date 2020/5/23
 */
public class OrderView {

    private Integer pId;

    private LocalDateTime pCreateTimeStamp;

    private BigDecimal pTotalPrice;

    private Integer pliId;

    private Integer pliPurchaseId;

    private Integer pliIngredientId;

    private Integer pliUnits;

    public OrderView() {
    }

    public OrderView(Integer pId, LocalDateTime pCreateTimeStamp, BigDecimal pTotalPrice, Integer pliId,
                     Integer pliPurchaseId, Integer pliIngredientId, Integer pliUnits) {
        this.pId = pId;
        this.pCreateTimeStamp = pCreateTimeStamp;
        this.pTotalPrice = pTotalPrice;
        this.pliId = pliId;
        this.pliPurchaseId = pliPurchaseId;
        this.pliIngredientId = pliIngredientId;
        this.pliUnits = pliUnits;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public LocalDateTime getpCreateTimeStamp() {
        return pCreateTimeStamp;
    }

    public void setpCreateTimeStamp(LocalDateTime pCreateTimeStamp) {
        this.pCreateTimeStamp = pCreateTimeStamp;
    }

    public BigDecimal getpTotalPrice() {
        return pTotalPrice;
    }

    public void setpTotalPrice(BigDecimal pTotalPrice) {
        this.pTotalPrice = pTotalPrice;
    }

    public Integer getPliId() {
        return pliId;
    }

    public void setPliId(Integer pliId) {
        this.pliId = pliId;
    }

    public Integer getPliPurchaseId() {
        return pliPurchaseId;
    }

    public void setPliPurchaseId(Integer pliPurchaseId) {
        this.pliPurchaseId = pliPurchaseId;
    }

    public Integer getPliIngredientId() {
        return pliIngredientId;
    }

    public void setPliIngredientId(Integer pliIngredientId) {
        this.pliIngredientId = pliIngredientId;
    }

    public Integer getPliUnits() {
        return pliUnits;
    }

    public void setPliUnits(Integer pliUnits) {
        this.pliUnits = pliUnits;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OrderView.class.getSimpleName() + "[", "]")
                .add("pId=" + pId)
                .add("pCreateTimeStamp=" + pCreateTimeStamp)
                .add("pTotalPrice=" + pTotalPrice)
                .add("pliId=" + pliId)
                .add("pliPurchaseId=" + pliPurchaseId)
                .add("pliIngredientId=" + pliIngredientId)
                .add("pliUnits=" + pliUnits)
                .toString();
    }
}
