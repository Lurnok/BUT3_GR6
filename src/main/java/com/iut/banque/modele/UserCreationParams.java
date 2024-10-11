package com.iut.banque.modele;

public class UserCreationParams {
   private String nom;
   private String prenom;
   private String adresse;
   private boolean male;
   private String usrId;
   private String usrPwd;
   private boolean manager;
   private String numClient;

   private UserCreationParams(Builder builder) {
       this.nom = builder.nom;
       this.prenom = builder.prenom;
       this.adresse = builder.adresse;
       this.male = builder.male;
       this.usrId = builder.usrId;
       this.usrPwd = builder.usrPwd;
       this.manager = builder.manager;
       this.numClient = builder.numClient;
   }

   // Getters
   public String getNom() { return nom; }
   public String getPrenom() { return prenom; }
   public String getAdresse() { return adresse; }
   public boolean isMale() { return male; }
   public String getUsrId() { return usrId; }
   public String getUsrPwd() { return usrPwd; }
   public boolean isManager() { return manager; }
   public String getNumClient() { return numClient; }


   // Classe builder utilisé afin que le constructeur de UserCreationParams ne prenne pas 8 paramètres.
   public static class Builder {
       private String nom;
       private String prenom;
       private String adresse;
       private boolean male;
       private String usrId;
       private String usrPwd;
       private boolean manager;
       private String numClient;

       public Builder setNom(String nom) {
           this.nom = nom;
           return this;
       }

       public Builder setPrenom(String prenom) {
           this.prenom = prenom;
           return this;
       }

       public Builder setAdresse(String adresse) {
           this.adresse = adresse;
           return this;
       }

       public Builder setMale(boolean male) {
           this.male = male;
           return this;
       }

       public Builder setUsrId(String usrId) {
           this.usrId = usrId;
           return this;
       }

       public Builder setUsrPwd(String usrPwd) {
           this.usrPwd = usrPwd;
           return this;
       }

       public Builder setManager(boolean manager) {
           this.manager = manager;
           return this;
       }

       public Builder setNumClient(String numClient) {
           this.numClient = numClient;
           return this;
       }

       public UserCreationParams build() {
           return new UserCreationParams(this);
       }
   }
}