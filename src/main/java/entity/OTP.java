package entity;

import java.util.Date;

public class OTP {
    private int id;
    private int userId;
    private String otp;
    private Date expirationTime;
    private boolean verified;

    public OTP() { }

    public OTP(int id, int userId, String otp, Date expirationTime, boolean verified) {
        this.id = id;
        this.userId = userId;
        this.otp = otp;
        this.expirationTime = expirationTime;
        this.verified = verified;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getOtp() {
        return otp;
    }
    public void setOtp(String otp) {
        this.otp = otp;
    }
    public Date getExpirationTime() {
        return expirationTime;
    }
    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }
    public boolean isVerified() {
        return verified;
    }
    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
