package comw.example.rplrus26.midsemester12rpl;

public class Member {

    private String id;
    private String username;
    private String deskripsi;
    private String photoId;
    private String tanggal;

    public Member() {
        this.setID(id);
        this.setUsername(username);
        this.setDeskripsi(deskripsi);
        this.setPhotoId(photoId);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getID()
    {
        return this.id;
    }

    public void setID(String id)
    {
        this.id = id;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }


    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
