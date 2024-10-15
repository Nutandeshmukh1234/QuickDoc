package com.example.quickdoc;

public class POJOCategoryWiseDoctorList {
    String id, doctorimage ,doctorname ,hospitalname,doctorfield, doctorexperience,doctorprice,
            doctorrating , doctortime, doctordescription;

    public POJOCategoryWiseDoctorList(String id, String doctorimage, String doctorname, String hospitalname,
                                      String doctorfield, String doctorexperience, String doctorprice, String doctorrating,
                                      String doctortime, String doctordescription)
    {
        this.id = id;
        this.doctorimage = doctorimage;
        this.doctorname = doctorname;
        this.hospitalname = hospitalname;
        this.doctorfield = doctorfield;
        this.doctorexperience =doctorexperience;
        this.doctorprice = doctorprice;
        this.doctorrating = doctorrating;
        this.doctortime = doctortime;
        this.doctordescription = doctordescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorimage() {
        return doctorimage;
    }

    public void setDoctorimage(String doctorimage) {
        this.doctorimage = doctorimage;
    }
    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getHospitalname() {
        return hospitalname;
    }

    public void setHospitalname(String hospitalname) {
        this.hospitalname = hospitalname;
    }

    public String getDoctorfield() {
        return doctorfield;
    }

    public void setDoctorfield(String doctorfield) {
        this.doctorfield = doctorfield;
    }

    public String getDoctorexperience() {
        return doctorexperience;
    }

    public void setDoctorexperience(String doctorexperience) {
        this.doctorexperience = doctorexperience;
    }


    public String getDoctorprice() {
        return doctorprice;
    }

    public void setDoctorprice(String doctorprice) {
        this.doctorprice = doctorprice;
    }

    public String getDoctorrating() {
        return doctorrating;
    }

    public void setDoctorrating(String doctorrating) {
        this.doctorrating = doctorrating;
    }

    public String getDoctortime() {
        return doctortime;
    }

    public void setDoctortime(String doctortime) {
        this.doctortime = doctortime;
    }

    public String getDoctordescription() {
        return doctordescription;
    }

    public void setDoctordescription(String doctordescription) {
        this.doctordescription = doctordescription;
    }
}
