package com.example.marketplacemain.marketplacemain.autenticacion.DTO;

import jakarta.validation.constraints.NotBlank;

public class UserChangePasswordDTO {

    @NotBlank
    private String oldpassword;

    @NotBlank
    private String newpassword;

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }


    


}
