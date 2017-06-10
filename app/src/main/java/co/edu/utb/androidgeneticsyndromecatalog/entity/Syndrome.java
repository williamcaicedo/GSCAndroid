package co.edu.utb.androidgeneticsyndromecatalog.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by william on 14/06/16.
 */
public class Syndrome implements Parcelable {



    private int id;
    private String name;
    private String synonym;
    private String site;
    private String geneLocus;
    private String geneticAnomaly;
    private String inheritance;
    private String retardation;
    private String retardationNotes;
    private String evolution;
    private String clinicalExams;
    private String omimLink;
    private String[] bibliography;
    private List<Feature> features;

    public Syndrome(){}

    public Syndrome(int id, String name, String synonym, String site, String geneLocus,
                    String geneticAnomaly, String inheritance, String retardation,
                    String retardationNotes, String evolution, String clinicalExams,
                    String omimLink, String[] bibliography, List<Feature> features) {

        this.id = id;
        this.name = name;
        this.synonym = synonym;
        this.site = site;
        this.geneLocus = geneLocus;
        this.geneticAnomaly = geneticAnomaly;
        this.inheritance = inheritance;
        this.retardation = retardation;
        this.retardationNotes = retardationNotes;
        this.evolution = evolution;
        this.clinicalExams = clinicalExams;
        this.omimLink = omimLink;
        this.bibliography = bibliography;
        this.features = features;
    }

    public Syndrome(Parcel in){
        this.id = in.readInt();
        this.name = in.readString();
        this.synonym = in.readString();
        this.site = in.readString();
        this.geneLocus = in.readString();
        this.geneticAnomaly = in.readString();
        this.inheritance = in.readString();
        this.retardation = in.readString();
        this.retardationNotes = in.readString();
        this.evolution = in.readString();
        this.clinicalExams = in.readString();
        this.omimLink = in.readString();
        List<String> bib = new ArrayList<>();
        in.readStringList(bib);
        bibliography = bib.toArray(new String[]{});
        features = Arrays.asList(in.createTypedArray(Feature.CREATOR));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public String getInheritance() {
        return inheritance;
    }

    public void setInheritance(String inheritance) {
        this.inheritance = inheritance;
    }

    public String getRetardation() {
        return retardation;
    }

    public void setRetardation(String retardation) {
        this.retardation = retardation;
    }

    public String getRetardationNotes() {
        return retardationNotes;
    }

    public void setRetardationNotes(String retardationNotes) {
        this.retardationNotes = retardationNotes;
    }

    public String getEvolution() {
        return evolution;
    }

    public void setEvolution(String evolution) {
        this.evolution = evolution;
    }

    public String getClinicalExams() {
        return clinicalExams;
    }

    public void setClinicalExams(String clinicalExams) {
        this.clinicalExams = clinicalExams;
    }

    public String[] getBibliography() {
        return bibliography;
    }

    public void setBibliography(String[] bibliography) {
        this.bibliography = bibliography;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getGeneLocus() {
        return geneLocus;
    }

    public void setGeneLocus(String geneLocus) {
        this.geneLocus = geneLocus;
    }

    public String getGeneticAnomaly() {
        return geneticAnomaly;
    }

    public void setGeneticAnomaly(String geneticAnomaly) {
        this.geneticAnomaly = geneticAnomaly;
    }

    public String getOmimLink() {
        return omimLink;
    }

    public void setOmimLink(String omimLink) {
        this.omimLink = omimLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(synonym);
        dest.writeString(site);
        dest.writeString(geneLocus);
        dest.writeString(geneticAnomaly);
        dest.writeString(inheritance);
        dest.writeString(retardation);
        dest.writeString(retardationNotes);
        dest.writeString(evolution);
        dest.writeString(clinicalExams);
        dest.writeString(omimLink);
        dest.writeStringList(Arrays.asList(bibliography));
        dest.writeTypedArray(features.toArray(new Feature[features.size()]),0);
    }

    public static final Parcelable.Creator<Syndrome> CREATOR = new Parcelable.Creator<Syndrome>() {
        public Syndrome createFromParcel(Parcel in) {
            return new Syndrome(in);
        }

        public Syndrome[] newArray(int size) {
            return new Syndrome[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Syndrome syndrome = (Syndrome) o;

        return id == syndrome.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
