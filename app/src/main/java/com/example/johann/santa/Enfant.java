package com.example.johann.santa;

import android.os.Parcel;
import android.os.Parcelable;

public class Enfant implements Parcelable {

    private String _sexe;
    private String _prenom;
    private String _annee_naissance;

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new CreatorImplementation();

    //Regular constructor
    public Enfant(String sexe, String prenom, String annee) {
        super();
        this._sexe = sexe;
        this._prenom = prenom;
        this._annee_naissance = annee;
    }

    //Constructor from parcel
    public Enfant(Parcel in) {
        super();
        this._sexe = in.readString();
        this._prenom = in.readString();
        this._annee_naissance = in.readString();
    }

    public void setNom(String sexe) {
        this._sexe = sexe;
    }

    public void setPrenom(String prenom) {
        this._prenom = prenom;
    }

    public void set_annee_naissance(String annee_naissance) {
        this._prenom = annee_naissance;
    }

    public String getSexe() {
        return this._sexe;
    }

    public String getPrenom() {
        return this._prenom;
    }

    public String get_annee_naissance() {
        return this._annee_naissance;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._sexe);
        dest.writeString(this._prenom);
        dest.writeString(this._annee_naissance);
    }

    @SuppressWarnings("rawtypes")
    private static final class CreatorImplementation implements Parcelable.Creator {
        public Enfant createFromParcel(Parcel in) {
            return new Enfant(in);
        }

        @Override
        public Object[] newArray(int size) {
            // TODO Auto-generated method stub
            return null;
        }
    }
}