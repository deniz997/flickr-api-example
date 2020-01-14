package com.example.flickrapiexample.Model;

import java.util.ArrayList;

//POJO Class for JsonResponse to Object Convertion

public class Photos {
    int page;
    int pages;
    int perpage;
    int total;

    public void setPage(int page) {
        this.page = page;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setPhoto(ArrayList<Photo> photo) {
        this.photo = photo;
    }

    ArrayList<Photo> photo;

    public int getPage() {
        return page;
    }

    public int getPages() {
        return pages;
    }

    public int getPerpage() {
        return perpage;
    }

    public int getTotal() {
        return total;
    }

    public ArrayList<Photo> getPhotos() {
        return photo;
    }


    //POJO Class for JsonResponse to Object Convertion
    public class Photo {
        private String id;
        private String owner;
        private String secret;
        private String server;
        private int farm;
        private String title;
        private int ispublic;
        private int isfriend;
        private int isfamily;

        public void setId(String id) {
            this.id = id;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public void setFarm(int farm) {
            this.farm = farm;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setIspublic(int ispublic) {
            this.ispublic = ispublic;
        }

        public void setIsfriend(int isfriend) {
            this.isfriend = isfriend;
        }

        public void setIsfamily(int isfamily) {
            this.isfamily = isfamily;
        }

        public int getIspublic() {
            return ispublic;
        }

        public int getIsfriend() {
            return isfriend;
        }

        public int getIsfamily() {
            return isfamily;
        }

        public String getId() {
            return id;
        }

        public String getOwner() {
            return owner;
        }

        public String getSecret() {
            return secret;
        }

        public String getServer() {
            return server;
        }

        public int getFarm() {
            return farm;
        }

        public String getTitle() {
            return title;
        }
    }

}
