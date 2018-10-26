package cloud.wayne.com.cloudiaryexample;

import com.cloudinary.Api;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;

import java.util.ArrayList;

public class ContentsCollection {
    public static ArrayList<Contents> getContents() {
        ArrayList<Contents> mContentsArrayList = new ArrayList<>();
        /*Contents mContents = new Contents();
        mContents.setName("Dick");
        mContents.setUrl("dick.jpg");
        mContentsArrayList.add(mContents);
        mContents = new Contents();
        mContents.setName("Sento");
        mContents.setUrl("Sento.jpg");
        mContentsArrayList.add(mContents);
        mContents = new Contents();
        mContents.setName("Also dick");
        mContents.setUrl("dick_2.jpg");
        mContentsArrayList.add(mContents);
        return mContentsArrayList;*/
        Cloudinary cloudinary = new Cloudinary(MyConfiguration.getMyConfigs());
        Api api = cloudinary.api();
        JSONObject outerObject = null;
        String jsonNext = null;
        boolean ifWeHaveMoreResources = true;
        while (ifWeHaveMoreResources) {
            try {
                outerObject = new JSONObject(
                    api.resources(ObjectUtils.asMap("max_results", 500, "next_cursor", jsonNext)));
                if (outerObject.has("next_cursor")) {
                    jsonNext = outerObject.get("next_cursor").toString();
                    ifWeHaveMoreResources = true;
                } else {
                    ifWeHaveMoreResources = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        JSONArray jsonArray = outerObject.getJSONArray("samples");
        for (int i = 0, size = jsonArray.length(); i < size; i++) {
            JSONObject objectInArray = jsonArray.getJSONObject(i);
            String public_id = objectInArray.get("public_id").toString();
            String url = objectInArray.get("secure_url").toString();
            Contents mContents = new Contents();
            mContents.setName(public_id);
            mContents.setUrl("url");
        }
        return mContentsArrayList;
    }
}
