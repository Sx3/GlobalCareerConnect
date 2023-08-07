package lk.gcc.model;

import jakarta.persistence.*;

@Entity
@Table(name = "consultant", schema = "gcc", catalog = "")
public class ConsultantEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "fname", nullable = false, length = 100)
    private String fname;
    @Basic
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    @Basic
    @Column(name = "phone", nullable = false, length = 50)
    private String phone;
    @Basic
    @Column(name = "spec_country", nullable = false, length = 50)
    private String specCountry;
    @Basic
    @Column(name = "job_type", nullable = false, length = 50)
    private String jobType;
    @Basic
    @Column(name = "uname", nullable = false, length = 50)
    private String uname;
    @Basic
    @Column(name = "psw", nullable = false, length = 50)
    private String psw;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpecCountry() {
        return specCountry;
    }

    public void setSpecCountry(String specCountry) {
        this.specCountry = specCountry;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConsultantEntity that = (ConsultantEntity) o;

        if (id != that.id) return false;
        if (fname != null ? !fname.equals(that.fname) : that.fname != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (specCountry != null ? !specCountry.equals(that.specCountry) : that.specCountry != null) return false;
        if (jobType != null ? !jobType.equals(that.jobType) : that.jobType != null) return false;
        if (uname != null ? !uname.equals(that.uname) : that.uname != null) return false;
        if (psw != null ? !psw.equals(that.psw) : that.psw != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (fname != null ? fname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (specCountry != null ? specCountry.hashCode() : 0);
        result = 31 * result + (jobType != null ? jobType.hashCode() : 0);
        result = 31 * result + (uname != null ? uname.hashCode() : 0);
        result = 31 * result + (psw != null ? psw.hashCode() : 0);
        return result;
    }
}
