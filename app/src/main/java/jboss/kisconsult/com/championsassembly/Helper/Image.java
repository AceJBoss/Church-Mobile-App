package jboss.kisconsult.com.championsassembly.Helper;

import com.android.volley.toolbox.ImageLoader;
import jboss.kisconsult.com.championsassembly.App.EndPoints;
import jboss.kisconsult.com.championsassembly.App.MyApplication;
import jboss.kisconsult.com.championsassembly.R;

import de.hdodenhof.circleimageview.CircleImageView;
import jboss.kisconsult.com.championsassembly.App.EndPoints;
import jboss.kisconsult.com.championsassembly.App.MyApplication;

public class Image {
    ImageLoader imageLoader;

    public Image(final CircleImageView image, String path) {
        path = EndPoints.IMG_BASE_URL + "/" + path;
        imageLoader = MyApplication.getInstance().getImageLoader();
        imageLoader.get(path, ImageLoader.getImageListener(
                image, R.drawable.profile, R.drawable.profile));
    }
}
