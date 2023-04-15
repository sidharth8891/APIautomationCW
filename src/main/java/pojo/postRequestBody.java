package pojo;

import java.util.List;

public class postRequestBody {
    private String name;
    private String job;

    private List<String> languages;

    private List<cityRequest> cityRequestBody;

    public List<cityRequest> getCityRequestBody() {
        return cityRequestBody;
    }

    public void setCityRequestBody(List<cityRequest> cityRequestBody) {
        this.cityRequestBody = cityRequestBody;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
