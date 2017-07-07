package com.example.administrator.myweather.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myweather.Application.MyApplication;
import com.example.administrator.myweather.MainActivity;
import com.example.administrator.myweather.R;
import com.example.administrator.myweather.Url.ConnentUrl;
import com.example.administrator.myweather.bean.City;
import com.example.administrator.myweather.utils.PrefUtil;
import com.example.administrator.myweather.utils.Utility;
import com.example.administrator.myweather.utils.localUtils;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class WelcomeActivity extends Activity {

    private String result = "01|北京,02|上海,03|天津,04|重庆,05|黑龙江,06|吉林,07|辽宁,08|内蒙古,09|河北,10|山西,11|陕西,12|山东," +
            "13|新疆,14|西藏,15|青海,16|甘肃,17|宁夏,18|河南,19|江苏,20|湖北,21|浙江,22|安徽,23|福建,24|江西,25|湖南,26|贵州,27|四川,28|广东,29|云南,30|广西,31|海南,32|香港,33|澳门,34|台湾";

    private String city = "0101|北京,0201|上海,0301|天津,0401|重庆,0501|哈尔滨,0502|齐齐哈尔,0503|牡丹江,0504|佳木斯,0505|绥化,0506|黑河,0507|大兴安岭,0508|伊春,0509|大庆,0510|七台河,0511|鸡西," +
            "0512|鹤岗,0513|双鸭山,0601|长春,0602|吉林,0603|延吉,0604|四平,0605|通化,0606|白城,0607|辽源,0608|松原,0609|白山,0701|沈阳,0702|大连,0703|鞍山,0704|抚顺,0705|本溪,0706|丹东,0707|锦州," +
            "0708|营口,0709|阜新,0710|辽阳,0711|铁岭,0712|朝阳,0713|盘锦,0714|葫芦岛,0801|呼和浩特,0802|包头,0803|乌海,0804|乌兰察布,0805|通辽,0806|赤峰,0807|鄂尔多斯,0808|巴彦淖尔,0809|锡林郭勒盟," +
            "0810|呼伦贝尔,0811|兴安盟,0812|阿拉善盟,0901|石家庄,0902|保定,0903|张家口,0904|承德,0905|唐山,0906|廊坊,0907|沧州,0908|衡水,0909|邢台,0910|邯郸,0911|秦皇岛,1001|太原,1002|大同,1003|阳泉," +
            "1004|晋中,1005|长治,1006|晋城,1007|临汾,1008|运城,1009|朔州,1010|忻州,1011|吕梁,1101|西安,1102|咸阳,1103|延安,1104|榆林,1105|渭南,1106|商洛,1107|安康,1108|汉中,1109|宝鸡,1110|铜川,1201|济南," +
            "1202|青岛,1203|淄博,1204|德州,1205|烟台,1206|潍坊,1207|济宁,1208|泰安,1209|临沂,1210|菏泽,1211|滨州,1212|东营,1213|威海,1214|枣庄,1215|日照,1216|莱芜,1217|聊城,1301|乌鲁木齐,1302|克拉玛依," +
            "1303|石河子,1304|昌吉,1305|吐鲁番,1306|巴音郭楞,1307|阿拉尔,1308|阿克苏,1309|喀什,1310|伊犁,1311|塔城,1312|哈密,1313|和田,1314|阿勒泰,1315|克州,1316|博尔塔拉,1401|拉萨,1402|日喀则,1403|山南," +
            "1404|林芝,1405|昌都,1406|那曲,1407|阿里,1501|西宁,1502|海东,1503|黄南,1504|海南,1505|果洛,1506|玉树,1507|海西,1508|海北,1601|兰州,1602|定西,1603|平凉,1604|庆阳,1605|武威,1606|金昌,1607|张掖," +
            "1608|酒泉,1609|天水,1610|陇南,1611|临夏,1612|甘南,1613|白银,1701|银川,1702|石嘴山,1703|吴忠,1704|固原,1705|中卫,1801|郑州,1802|安阳,1803|新乡,1804|许昌,1805|平顶山,1806|信阳,1807|南阳,1808|开封," +
            "1809|洛阳,1810|商丘,1811|焦作,1812|鹤壁,1813|濮阳,1814|周口,1815|漯河,1816|驻马店,1817|三门峡,1818|济源,1901|南京,1902|无锡,1903|镇江,1904|苏州,1905|南通,1906|扬州,1907|盐城,1908|徐州,1909|淮安," +
            "1910|连云港,1911|常州,1912|泰州,1913|宿迁,2001|武汉,2002|襄阳,2003|鄂州,2004|孝感,2005|黄冈,2006|黄石,2007|咸宁,2008|荆州,2009|宜昌,2010|恩施,2011|十堰,2012|神农架,2013|随州,2014|荆门,2015|天门," +
            "2016|仙桃,2017|潜江,2101|杭州,2102|湖州,2103|嘉兴,2104|宁波,2105|绍兴,2106|台州,2107|温州,2108|丽水,2109|金华,2110|衢州,2111|舟山,2201|合肥,2202|蚌埠,2203|芜湖,2204|淮南,2205|马鞍山,2206|安庆,2207" +
            "|宿州,2208|阜阳,2209|亳州,2210|黄山,2211|滁州,2212|淮北,2213|铜陵,2214|宣城,2215|六安,2216|巢湖,2217|池州,2301|福州,2302|厦门,2303|宁德,2304|莆田,2305|泉州,2306|漳州,2307|龙岩,2308|三明,2309|南平," +
            "2401|南昌,2402|九江,2403|上饶,2404|抚州,2405|宜春,2406|吉安,2407|赣州,2408|景德镇,2409|萍乡,2410|新余,2411|鹰潭,2501|长沙,2502|湘潭,2503|株洲,2504|衡阳,2505|郴州,2506|常德,2507|益阳,2508|娄底,2509" +
            "|邵阳,2510|岳阳,2511|张家界,2512|怀化,2513|黔阳,2514|永州,2515|湘西,2601|贵阳,2602|遵义,2603|安顺,2604|黔南,2605|黔东南,2606|铜仁,2607|毕节,2608|六盘水,2609|黔西南,2701|成都,2702|攀枝花,2703|自贡," +
            "2704|绵阳,2705|南充,2706|达州,2707|遂宁,2708|广安,2709|巴中,2710|泸州,2711|宜宾,2712|内江,2713|资阳,2714|乐山,2715|眉山,2716|凉山,2717|雅安,2718|甘孜,2719|阿坝,2720|德阳,2721|广元,2801|广州,2802|" +
            "韶关,2803|惠州,2804|梅州,2805|汕头,2806|深圳,2807|珠海,2808|佛山,2809|肇庆,2810|湛江,2811|江门,2812|河源,2813|清远,2814|云浮,2815|潮州,2816|东莞,2817|中山,2818|阳江,2819|揭阳,2820|茂名,2821|汕尾,2822|" +
            "东沙岛,2901|昆明,2902|大理,2903|红河,2904|曲靖,2905|保山,2906|文山,2907|玉溪,2908|楚雄,2909|思茅,2910|昭通,2911|临沧,2912|怒江,2913|迪庆,2914|丽江,2915|德宏,2916|西双版纳,3001|南宁,3002|崇左,3003|柳州," +
            "3004|来宾,3005|桂林,3006|梧州,3007|贺州,3008|贵港,3009|玉林,3010|百色,3011|钦州,3012|河池,3013|北海,3014|防城港,3101|海口,3102|琼山,3103|三亚,3104|东方,3105|临高,3106|澄迈,3107|儋州,3108|昌江,3109|白沙" +
            "3110|琼中,3111|定安,3112|屯昌,3113|琼海,3114|文昌,3115|清兰,3116|保亭,3117|万宁,3118|陵水,3119|西沙,3120|珊瑚岛,3121|永署礁,3122|南沙岛,3123|乐东,3124|通什,3201|香港,3301|澳门,3401|台北,3402|高雄," +
            "3403|台南,3404|台中,3405|桃园,3406|新竹,3407|宜兰,3408|澎湖,3409|嘉义,3410|花莲,3411|台东,3412|基隆";


    private static final int BAIDU_READ_PHONE_STATE =100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);

        if (Build.VERSION.SDK_INT>=23){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(getApplicationContext(),"没有权限,请手动开启定位权限",Toast.LENGTH_SHORT).show();
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
                ActivityCompat.requestPermissions(WelcomeActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, BAIDU_READ_PHONE_STATE);
            }else{
                init();
            }
        }else{
            init();
        }

    }


    public void init(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (TextUtils.isEmpty(PrefUtil.getString(WelcomeActivity.this, "isFirst", ""))) {

                    Log.i("FFirst", "onCreate:第一次启动 ");
                    PrefUtil.setString(WelcomeActivity.this, "isFirst", "noFirst");
                    try {
                        Utility.saveProvincesResponse(WelcomeActivity.this, result);
                        Utility.saveCitiesResponse(WelcomeActivity.this, city, "");

                    } catch (DbException e) {
                        e.printStackTrace();
                    }


                    new localUtils().showLocation(WelcomeActivity.this, new localUtils.CallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            Toast.makeText(WelcomeActivity.this,response,Toast.LENGTH_LONG).show();
                            PrefUtil.setString(WelcomeActivity.this,"City",response);
                            Intent intent = new Intent(WelcomeActivity.this, WeatherActivity.class);
                            startActivity(intent);
                            finish();

                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(WelcomeActivity.this,"定位失败，请手动选择城市",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(WelcomeActivity.this, SelectCityActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                }else {
                    Intent intent = new Intent(WelcomeActivity.this, WeatherActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        }, 2000);


    }

    //Android6.0申请权限的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("location_quanxian", "onRequestPermissionsResult:获取到权限");
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    init();
                } else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(WelcomeActivity.this,"获取位置权限失败，请手动选择城市",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(WelcomeActivity.this, SelectCityActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            default:
                break;
        }
    }
}
