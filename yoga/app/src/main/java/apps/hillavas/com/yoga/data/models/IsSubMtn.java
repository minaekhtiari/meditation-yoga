package apps.hillavas.com.yoga.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IsSubMtn {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("startTimeMillis")
    @Expose
    private Long startTimeMillis;
    @SerializedName("expiryTimeMillis")
    @Expose

    private Long expiryTimeMillis;
    @SerializedName("autoRenewing")
    @Expose
    private Boolean autoRenewing;
    @SerializedName("priceCurrencyCode")
    @Expose
    private String priceCurrencyCode;
    @SerializedName("priceAmountMicros")
    @Expose
    private Integer priceAmountMicros;
    @SerializedName("countryCode")
    @Expose
    private String countryCode;
    @SerializedName("paymentState")
    @Expose
    private Integer paymentState;
    @SerializedName("todayPayment")
    @Expose
    private Integer todayPayment;
    @SerializedName("totalPayment")
    @Expose
    private Integer totalPayment;
    @SerializedName("msisdn")
    @Expose
    private String msisdn;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Long getStartTimeMillis() {
        return startTimeMillis;
    }

    public void setStartTimeMillis(Long startTimeMillis) {
        this.startTimeMillis = startTimeMillis;
    }

    public Long getExpiryTimeMillis() {
        return expiryTimeMillis;
    }

    public void setExpiryTimeMillis(Long expiryTimeMillis) {
        this.expiryTimeMillis = expiryTimeMillis;
    }

    public Boolean getAutoRenewing() {
        return autoRenewing;
    }

    public void setAutoRenewing(Boolean autoRenewing) {
        this.autoRenewing = autoRenewing;
    }

    public String getPriceCurrencyCode() {
        return priceCurrencyCode;
    }

    public void setPriceCurrencyCode(String priceCurrencyCode) {
        this.priceCurrencyCode = priceCurrencyCode;
    }

    public Integer getPriceAmountMicros() {
        return priceAmountMicros;
    }

    public void setPriceAmountMicros(Integer priceAmountMicros) {
        this.priceAmountMicros = priceAmountMicros;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(Integer paymentState) {
        this.paymentState = paymentState;
    }

    public Integer getTodayPayment() {
        return todayPayment;
    }

    public void setTodayPayment(Integer todayPayment) {
        this.todayPayment = todayPayment;
    }

    public Integer getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Integer totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

}
