package com.example.appnhom9;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends BaseActivity {

    private EditText searchBar, noteEditText;
    private ImageView notificationIcon, navHome, navProfile, navSettings, viewNotesIcon, navHospital;
    private TextView summaryTitle, stepCountText, saveButton;
    private CalendarView calendarView;
    private RecyclerView articleRecyclerView;

    private ArticleAdapter articleAdapter;
    private List<Article> articleList = new ArrayList<>();
    private List<Article> filteredArticleList = new ArrayList<>();
    private String fileName = "";

    private final String[] healthTips = {
            "💧 Đừng quên uống đủ 2 lít nước mỗi ngày!",
            "🏃‍♀️ Vận động 30 phút mỗi ngày để giữ dáng và tốt cho tim mạch.",
            "🥗 Ăn nhiều rau xanh và trái cây để bổ sung vitamin tự nhiên.",
            "😴 Ngủ đủ 7–8 tiếng mỗi đêm giúp cơ thể hồi phục tốt hơn.",
            "🚶‍♂️ Mỗi giờ, hãy đứng dậy và đi lại 5 phút để tránh đau lưng.",
            "🧘‍♀️ Thiền hoặc hít thở sâu giúp bạn giảm căng thẳng hiệu quả.",
            "🍵 Thay cà phê bằng trà xanh để tinh thần tỉnh táo hơn.",
            "🧴 Bôi kem chống nắng mỗi ngày, kể cả khi trời râm.",
            "🦷 Đánh răng và dùng chỉ nha khoa để giữ nụ cười tươi sáng.",
            "📵 Tắt điện thoại trước khi ngủ giúp bạn dễ ngủ và sâu giấc hơn."
    };
    private int currentTipIndex = 0;
    private final Handler tipHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView dinhduongIcon = findViewById(R.id.dinhduong_icon); // Đảm bảo rằng ID này đúng với ID của ImageView
        dinhduongIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở Activity hoặc Fragment hiển thị dinh dưỡng
                Intent intent = new Intent(MainActivity.this, dinhduongActivity.class);
                startActivity(intent);
            }
        });
        // mở socuu
        ImageView nutritionKnowledgeCard = findViewById(R.id.nav_nutrition_knowledge);

        nutritionKnowledgeCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, socuuActivity.class);
            startActivity(intent);
        });

        initViews();
        populateArticleList();
        setupRecyclerView();
        setupSearchFunction();
        setupCalendarView();
        setupNavigation();

        summaryTitle.setText(healthTips[currentTipIndex]);
        startHealthTipRotation();

        viewNotesIcon.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, NotesListActivity.class)));
        notificationIcon.setOnClickListener(v -> Toast.makeText(this, "Bạn không có thông báo mới", Toast.LENGTH_SHORT).show());
        saveButton.setOnClickListener(v -> saveNoteForSelectedDate());
    }

    private void initViews() {
        searchBar = findViewById(R.id.searchBar);
        noteEditText = findViewById(R.id.noteEditText);
        saveButton = findViewById(R.id.saveButton);
        calendarView = findViewById(R.id.calendarView);
        articleRecyclerView = findViewById(R.id.articleRecyclerView);
        summaryTitle = findViewById(R.id.summaryTitle);
        notificationIcon = findViewById(R.id.notificationIcon);
        navHome = findViewById(R.id.nav_home);
        navProfile = findViewById(R.id.nav_profile);
        navSettings = findViewById(R.id.navSettings);
        viewNotesIcon = findViewById(R.id.viewNotesIcon);
        navHospital = findViewById(R.id.nav_hospital); // Thêm dòng này
    }

    private void populateArticleList() {
        articleList.add(new Article(
                "Tại sao sức khoẻ thính giác lại quan trọng",
                "Tìm hiểu chi tiết về thính giác của bạn và cách chăm sóc thính giác",
                "Thính giác là một trong năm giác quan thiết yếu của con người, giữ vai trò then chốt trong việc tiếp nhận và xử lý âm thanh từ môi trường xung quanh. Nhờ có khả năng nghe, con người có thể giao tiếp một cách hiệu quả thông qua ngôn ngữ nói, từ đó xây dựng các mối quan hệ xã hội, học hỏi kiến thức mới và phát triển tư duy. Đặc biệt, đối với trẻ nhỏ, thính giác có vai trò cực kỳ quan trọng trong quá trình phát triển ngôn ngữ và khả năng nhận thức. Khi khả năng nghe bị suy giảm, người bệnh không chỉ gặp khó khăn trong giao tiếp hàng ngày mà còn có nguy cơ bị cô lập xã hội, dẫn đến những tác động tiêu cực về mặt tâm lý như trầm cảm, lo âu hoặc giảm chất lượng cuộc sống.\n" +
                        "\n" +
                        "Trong thời đại hiện đại, con người ngày càng phải đối mặt với nguy cơ tổn thương tai do tiếp xúc với tiếng ồn lớn và kéo dài, đặc biệt là ở các môi trường đô thị, nhà máy, công trường xây dựng hoặc thậm chí trong những hoạt động giải trí như nghe nhạc bằng tai nghe hoặc đi xem hòa nhạc. Việc tiếp xúc thường xuyên với tiếng ồn có cường độ cao trên 85 decibel có thể làm hỏng các tế bào lông trong tai trong – những tế bào không thể tái sinh, dẫn đến tình trạng mất thính lực vĩnh viễn. Không giống như các vấn đề sức khỏe khác có thể phục hồi hoàn toàn, mất thính lực là một tổn thương không thể đảo ngược. Điều đáng tiếc là nhiều người không nhận thức được nguy cơ này cho đến khi đã quá muộn.\n" +
                        "\n" +
                        "Để bảo vệ thính lực, mỗi người nên có thói quen kiểm tra sức khỏe tai định kỳ, đặc biệt là khi nhận thấy những dấu hiệu bất thường như ù tai, nghe kém, hoặc phải tăng âm lượng khi xem tivi hay nghe điện thoại. Những kiểm tra đơn giản nhưng định kỳ sẽ giúp phát hiện sớm các vấn đề về tai và có hướng can thiệp kịp thời. Ngoài ra, việc sử dụng tai nghe đúng cách là điều đặc biệt quan trọng – người dùng không nên đeo tai nghe quá lâu, đặc biệt là trong không gian yên tĩnh, và nên giữ âm lượng ở mức dưới 60% âm lượng tối đa của thiết bị.\n" +
                        "\n" +
                        "Không chỉ âm thanh mà thói quen vệ sinh tai cũng ảnh hưởng lớn đến sức khỏe tai. Nhiều người có thói quen dùng tăm bông hoặc vật nhọn để ngoáy tai sâu, điều này có thể gây tổn thương ống tai hoặc màng nhĩ, thậm chí làm tăng nguy cơ nhiễm trùng tai. Thực tế, tai có cơ chế tự làm sạch thông qua việc dịch nhầy và ráy tai tự đẩy ra ngoài. Vì vậy, việc vệ sinh tai nên được thực hiện nhẹ nhàng ở phần ngoài của tai, tránh đưa dị vật vào sâu bên trong.\n" +
                        "\n" +
                        "Ngoài ra, cần lưu ý bảo vệ tai trong môi trường khói bụi, hóa chất, hoặc khi đi bơi để tránh nước bẩn xâm nhập vào ống tai gây viêm tai giữa. Việc đội mũ bảo hiểm, đeo tai chống ồn hoặc nút tai khi làm việc trong môi trường có nguy cơ cũng là biện pháp hiệu quả để phòng tránh tổn thương thính giác.\n" +
                        "\n" +
                        "Tóm lại, thính giác là tài sản vô giá cần được bảo vệ mỗi ngày. Việc chủ động chăm sóc tai không chỉ giúp phòng ngừa các bệnh lý nguy hiểm mà còn góp phần duy trì khả năng giao tiếp, học tập và tận hưởng cuộc sống một cách trọn vẹn nhất.",
                R.drawable.thinhgiac));

        articleList.add(new Article(
                "Rửa tay có thể bảo vệ sức khoẻ của bạn",
                "Tại sao việc rửa tay lại quan trọng và các mẹo để thực hiện đúng",
                "Rửa tay là một trong những hành động vệ sinh cá nhân tưởng chừng đơn giản nhưng lại đóng vai trò cực kỳ quan trọng trong việc bảo vệ sức khỏe, phòng ngừa sự lây lan của nhiều loại bệnh truyền nhiễm nguy hiểm. Các nghiên cứu y học và khuyến cáo từ Tổ chức Y tế Thế giới (WHO) đã chỉ ra rằng việc rửa tay đúng cách có thể làm giảm đáng kể nguy cơ mắc các bệnh phổ biến như cảm cúm, tiêu chảy cấp, viêm phổi, bệnh tay chân miệng và đặc biệt là các bệnh do virus như COVID-19. Đây là một biện pháp phòng bệnh hiệu quả, tiết kiệm và có thể áp dụng cho mọi người ở mọi độ tuổi, từ trẻ nhỏ cho đến người già.\n" +
                        "\n" +
                        "Trong cuộc sống hàng ngày, bàn tay của chúng ta thường xuyên tiếp xúc với rất nhiều bề mặt như tay nắm cửa, bàn ghế, điện thoại di động, máy tính, tiền mặt, và nhiều vật dụng khác. Những bề mặt này thường chứa vi khuẩn, virus và ký sinh trùng mà mắt thường không thể nhìn thấy được. Khi chúng ta vô thức chạm tay vào mặt, mắt, mũi hoặc miệng, các tác nhân gây bệnh có thể dễ dàng xâm nhập vào cơ thể, gây ra các bệnh truyền nhiễm. Do đó, hành động rửa tay không chỉ giúp làm sạch bụi bẩn mà còn là hàng rào đầu tiên ngăn chặn sự xâm nhập của vi sinh vật gây hại.\n" +
                        "\n" +
                        "Điều quan trọng là phải rửa tay đúng cách và đúng thời điểm. Theo các chuyên gia y tế, bạn nên rửa tay bằng xà phòng và nước sạch trong ít nhất 20 giây, đặc biệt sau khi đi vệ sinh, trước khi ăn, sau khi ho hoặc hắt hơi, sau khi tiếp xúc với vật nuôi, khi chăm sóc người bệnh hoặc khi từ nơi công cộng trở về nhà. Rửa tay bằng nước không thôi là chưa đủ, vì nước không thể loại bỏ hoàn toàn dầu mỡ, vi khuẩn và virus bám trên da. Xà phòng giúp phá vỡ lớp màng lipid của virus, từ đó tiêu diệt chúng một cách hiệu quả.\n" +
                        "\n" +
                        "Trong những trường hợp không có sẵn nước và xà phòng, bạn có thể sử dụng dung dịch rửa tay nhanh chứa cồn với nồng độ ít nhất 60%. Tuy nhiên, đây chỉ là biện pháp thay thế tạm thời và không thể thay thế hoàn toàn việc rửa tay bằng xà phòng. Ngoài ra, cần chú ý đến việc làm khô tay đúng cách sau khi rửa, bởi vì vi khuẩn có thể dễ dàng sinh sôi trên đôi tay ẩm ướt. Sử dụng khăn giấy dùng một lần hoặc máy sấy tay là lựa chọn an toàn và hợp vệ sinh hơn so với khăn vải dùng chung.\n" +
                        "\n" +
                        "Trong bối cảnh đại dịch toàn cầu như COVID-19, việc rửa tay thường xuyên không chỉ giúp bảo vệ bản thân mà còn là hành động có trách nhiệm với cộng đồng. Thói quen này giúp giảm gánh nặng cho hệ thống y tế, ngăn chặn sự lây lan của dịch bệnh và góp phần xây dựng một môi trường sống an toàn, lành mạnh.\n" +
                        "\n" +
                        "Giáo dục và xây dựng thói quen rửa tay từ nhỏ là điều vô cùng quan trọng. Cha mẹ, thầy cô và những người chăm sóc trẻ nên hướng dẫn trẻ em rửa tay đúng cách, giải thích vì sao hành động này lại cần thiết để bảo vệ sức khỏe. Ngoài ra, các trường học, nơi làm việc và khu vực công cộng nên lắp đặt các trạm rửa tay thuận tiện, cung cấp đầy đủ xà phòng, dung dịch sát khuẩn để khuyến khích mọi người giữ gìn vệ sinh cá nhân.\n" +
                        "\n" +
                        "Tóm lại, rửa tay không chỉ là một hành vi vệ sinh cá nhân mà còn là một “vũ khí thầm lặng” nhưng vô cùng hiệu quả trong cuộc chiến phòng chống bệnh tật. Hãy xây dựng thói quen rửa tay thường xuyên và đúng cách như một phần không thể thiếu trong cuộc sống hàng ngày để bảo vệ bản thân, gia đình và cộng đồng khỏi các mối đe dọa vô hình từ vi khuẩn và virus.",
                R.drawable.ruatay));

        articleList.add(new Article(
                "Tiểu đường: những điều bạn cần biết",
                "Hiểu rõ về bệnh tiểu đường và cách kiểm soát bệnh hiệu quả",
                "Tiểu đường là một bệnh lý mãn tính liên quan đến rối loạn chuyển hóa đường glucose trong máu. Nguyên nhân là do cơ thể không sản xuất đủ insulin – một loại hormone do tuyến tụy tiết ra để giúp đưa đường từ máu vào tế bào, hoặc do cơ thể kháng insulin, nghĩa là insulin không còn hoạt động hiệu quả nữa. Kết quả là lượng đường trong máu tăng cao kéo dài, gây ảnh hưởng nghiêm trọng đến nhiều cơ quan trong cơ thể.\n" +
                        "\n" +
                        "Tiểu đường được chia thành ba loại chính:\n" +
                        "\t1.\tTiểu đường type 1: Xảy ra khi hệ miễn dịch của cơ thể tấn công nhầm các tế bào beta ở tuyến tụy – nơi sản xuất insulin. Do đó, người bệnh cần tiêm insulin suốt đời. Bệnh thường xuất hiện ở trẻ em, thanh thiếu niên nhưng cũng có thể xảy ra ở người lớn.\n" +
                        "\t2.\tTiểu đường type 2: Chiếm khoảng 90% các ca tiểu đường. Cơ thể vẫn sản xuất insulin nhưng không sử dụng hiệu quả. Loại này thường xuất hiện ở người trưởng thành, đặc biệt là người có lối sống ít vận động, thừa cân, béo phì hoặc có tiền sử gia đình mắc bệnh.\n" +
                        "\t3.\tTiểu đường thai kỳ: Xảy ra trong thời kỳ mang thai, thường biến mất sau khi sinh. Tuy nhiên, phụ nữ từng bị tiểu đường thai kỳ có nguy cơ cao phát triển thành tiểu đường type 2 trong tương lai.\n" +
                        "\n" +
                        "Triệu chứng thường gặp:\n" +
                        "\t•\tKhát nước nhiều, tiểu nhiều lần trong ngày.\n" +
                        "\t•\tCảm thấy mệt mỏi, đói liên tục.\n" +
                        "\t•\tSụt cân không rõ nguyên nhân.\n" +
                        "\t•\tVết thương lâu lành, dễ nhiễm trùng.\n" +
                        "\t•\tNhìn mờ, tê bì tay chân.\n" +
                        "\n" +
                        "Nếu không được phát hiện và kiểm soát kịp thời, tiểu đường có thể gây ra các biến chứng nguy hiểm như:\n" +
                        "\t•\tBiến chứng tim mạch (tăng nguy cơ đột quỵ, nhồi máu cơ tim).\n" +
                        "\t•\tTổn thương thần kinh, dẫn đến mất cảm giác ở chân tay.\n" +
                        "\t•\tTổn thương thận, có thể dẫn đến suy thận.\n" +
                        "\t•\tBiến chứng mắt, như mù lòa do tổn thương võng mạc.\n" +
                        "\t•\tNhiễm trùng chân, có thể dẫn đến hoại tử và cắt cụt chi.\n" +
                        "\n" +
                        "Phòng ngừa và kiểm soát bệnh:\n" +
                        "\t•\tDuy trì chế độ ăn lành mạnh, nhiều rau xanh, chất xơ, ít đường và tinh bột tinh chế.\n" +
                        "\t•\tTập thể dục đều đặn, ít nhất 30 phút mỗi ngày.\n" +
                        "\t•\tKiểm soát cân nặng và tránh béo phì.\n" +
                        "\t•\tKiểm tra đường huyết định kỳ, đặc biệt nếu có yếu tố nguy cơ.\n" +
                        "\t•\tTuân thủ điều trị nếu đã được chẩn đoán, bao gồm dùng thuốc, tiêm insulin theo chỉ định bác sĩ.\n" +
                        "\n" +
                        "Tiểu đường không thể chữa khỏi hoàn toàn, nhưng nếu được kiểm soát tốt, người bệnh vẫn có thể sống khỏe mạnh, làm việc và sinh hoạt bình thường. Điều quan trọng là nâng cao nhận thức, phát hiện sớm, và hình thành thói quen sống lành mạnh để phòng ngừa và chung sống an toàn với căn bệnh này.\n",
                R.drawable.tieuduong));

        articleList.add(new Article(
                "Tìm hiểu mức oxy trong máu",
                "Những điều đó nghĩa là gì và tại sao chúng lại quan trọng với sức khoẻ của bạn",
                "Mức oxy trong máu là chỉ số phản ánh lượng oxy được gắn vào hemoglobin trong hồng cầu, từ đó cung cấp năng lượng cho các tế bào và duy trì hoạt động sống. Đây là một trong những dấu hiệu sinh tồn quan trọng nhất, đặc biệt trong các tình trạng bệnh lý về hô hấp như viêm phổi, hen suyễn, hoặc COVID-19.\n" +
                        "\n" +
                        "Oxy được vận chuyển từ phổi đến toàn bộ cơ thể thông qua máu. Khi nồng độ oxy giảm dưới mức bình thường, các cơ quan sẽ không được cung cấp đủ oxy, gây ra hiện tượng mệt mỏi, chóng mặt, khó thở hoặc trong trường hợp nghiêm trọng hơn là tổn thương mô.\n" +
                        "\n" +
                        "Chỉ số SpO2 (Saturation of Peripheral Oxygen) là cách đo nồng độ oxy phổ biến nhất hiện nay. Thông thường, chỉ số SpO2 dao động từ 95% đến 100% là bình thường. Nếu SpO2 dưới 94%, bạn nên theo dõi sát hoặc đến cơ sở y tế để được kiểm tra thêm. Dưới 90% là mức báo động, cần can thiệp y tế ngay lập tức.\n" +
                        "\n" +
                        "Hiện nay, máy đo nồng độ oxy trong máu dạng kẹp ngón tay (Pulse Oximeter) rất phổ biến, dễ sử dụng tại nhà. Ngoài ra, một số đồng hồ thông minh và thiết bị đeo tay hiện đại cũng có khả năng theo dõi SpO2 liên tục trong ngày hoặc khi ngủ.\n" +
                        "\n" +
                        "Bạn nên đo mức oxy trong máu định kỳ, đặc biệt nếu bạn:\n" +
                        "\t•\tCó bệnh lý hô hấp mãn tính như COPD, hen suyễn\n" +
                        "\t•\tĐã từng nhiễm COVID-19 nặng\n" +
                        "\t•\tHay bị khó thở hoặc mệt mỏi khi gắng sức\n" +
                        "\t•\tNgủ ngáy, ngưng thở khi ngủ hoặc rối loạn giấc ngủ\n" +
                        "\n" +
                        "Để duy trì mức oxy trong máu ổn định, hãy giữ cho phổi khỏe mạnh bằng cách không hút thuốc, tập thể dục đều đặn, ăn uống đầy đủ chất, và tránh tiếp xúc với môi trường ô nhiễm. Khi tập thể dục, bạn cũng nên luyện tập hít thở đúng cách để cải thiện khả năng hấp thu oxy.\n" +
                        "\n" +
                        "Cuối cùng, hãy lắng nghe cơ thể mình. Nếu bạn cảm thấy khó thở, chóng mặt, tim đập nhanh bất thường, hãy kiểm tra SpO2 ngay và đến cơ sở y tế nếu cần.",
                R.drawable.mau));

        articleList.add(new Article(
                "Tìm hiểu về nhịp tim của bạn",
                "Tìm hiểu xem nhịp tim cao hay thấp có ý nghĩa như thế nào",
                "Nhịp tim – hay còn gọi là tần số tim – là số lần tim đập trong một phút. Đây là một trong những chỉ số sức khỏe cơ bản và dễ theo dõi nhất, phản ánh mức độ hoạt động của hệ tim mạch và tình trạng thể chất tổng thể của bạn.\n" +
                        "\n" +
                        "Đối với người trưởng thành khỏe mạnh, nhịp tim nghỉ ngơi bình thường dao động từ 60 đến 100 nhịp/phút. Tuy nhiên, vận động viên hoặc người thường xuyên luyện tập thể thao có thể có nhịp tim thấp hơn, từ 40–60 nhịp/phút mà vẫn bình thường. Mức nhịp tim lý tưởng thường nằm trong khoảng 60–80 nhịp/phút, khi bạn đang nghỉ ngơi, thư giãn.\n" +
                        "\n" +
                        "Có nhiều yếu tố ảnh hưởng đến nhịp tim, bao gồm:\n" +
                        "\t•\tTình trạng sức khỏe: sốt, mất nước, cường giáp, lo âu, bệnh tim mạch…\n" +
                        "\t•\tCảm xúc: stress, hồi hộp, sợ hãi\n" +
                        "\t•\tHoạt động thể chất: nhịp tim sẽ tăng khi vận động mạnh và trở lại bình thường khi nghỉ ngơi\n" +
                        "\t•\tThuốc: caffeine, thuốc giảm đau, thuốc tim mạch…\n" +
                        "\n" +
                        "Tại sao cần theo dõi nhịp tim?\n" +
                        "\n" +
                        "Nhịp tim là chỉ báo cho tình trạng hoạt động của tim và hệ tuần hoàn. Nhịp tim quá cao (trên 100) khi nghỉ ngơi có thể cảnh báo nguy cơ bệnh tim mạch, rối loạn tuyến giáp hoặc căng thẳng mạn tính. Ngược lại, nhịp tim quá thấp (dưới 50) không do luyện tập hoặc thuốc có thể là dấu hiệu block nhĩ-thất hoặc rối loạn dẫn truyền trong tim.\n" +
                        "\n" +
                        "Cách theo dõi nhịp tim tại nhà:\n" +
                        "\t•\tDùng tay đo thủ công: Đặt hai ngón tay lên cổ tay hoặc cổ, đếm số nhịp trong 15 giây và nhân với 4.\n" +
                        "\t•\tDùng thiết bị thông minh: Đồng hồ thông minh, vòng đeo tay sức khỏe (smartband) hay máy đo huyết áp điện tử hiện đại đều có chức năng đo nhịp tim tự động.\n" +
                        "\t•\tỨng dụng theo dõi sức khỏe: Kết hợp cảm biến camera hoặc cảm biến quang học để đo nhịp tim thông qua app trên điện thoại.\n" +
                        "\n" +
                        "Làm sao để cải thiện nhịp tim ổn định hơn?\n" +
                        "\t•\tTập luyện thể dục đều đặn, đặc biệt là các bài aerobic, đi bộ, đạp xe\n" +
                        "\t•\tGiảm stress, ngủ đủ giấc và thư giãn tinh thần\n" +
                        "\t•\tHạn chế caffeine và các chất kích thích\n" +
                        "\t•\tDuy trì chế độ ăn tốt cho tim mạch, giàu omega-3, ít đường và muối\n" +
                        "\n" +
                        "Hãy lắng nghe nhịp tim của bạn như một thông điệp từ trái tim. Khi thấy nhịp tim bất thường, liên tục cao hoặc thấp kéo dài, kèm theo chóng mặt, ngất, khó thở – bạn nên đến bác sĩ để kiểm tra chuyên sâu hơn.",
                R.drawable.tim));

        articleList.add(new Article(
                "Theo dõi thuốc của bạn",
                "Tại sao việc theo dõi thuốc của bạn đang dùng lại quan trọng?",
                "Việc uống thuốc không chỉ đơn giản là ghi nhớ liều lượng, mà còn liên quan mật thiết đến hiệu quả điều trị và an toàn sức khỏe. Nhiều người bỏ qua bước theo dõi thuốc hằng ngày, dẫn đến tình trạng quên liều, dùng sai thuốc hoặc dùng thuốc trùng lặp – gây ảnh hưởng nghiêm trọng đến kết quả điều trị và thậm chí nguy hiểm đến tính mạng.\n" +
                        "\n" +
                        "Theo dõi thuốc giúp bạn:\n" +
                        "\t•\tDùng đúng thuốc, đúng liều, đúng thời gian: Đặc biệt quan trọng với người mắc bệnh mãn tính như tiểu đường, tăng huyết áp, rối loạn tuyến giáp, động kinh…\n" +
                        "\t•\tPhát hiện tác dụng phụ kịp thời: Ghi lại các dấu hiệu bất thường như chóng mặt, nổi mẩn, buồn nôn, hay mệt mỏi giúp bác sĩ điều chỉnh toa thuốc chính xác.\n" +
                        "\t•\tTránh tương tác thuốc: Một số loại thuốc khi dùng cùng nhau có thể giảm tác dụng hoặc gây phản ứng phụ nghiêm trọng.\n" +
                        "\t•\tTiết kiệm chi phí điều trị: Việc uống thuốc đều đặn giúp bệnh được kiểm soát tốt, tránh nhập viện hay phải điều trị bổ sung tốn kém.\n" +
                        "\n" +
                        "Cách theo dõi thuốc hiệu quả:\n" +
                        "\t1.\tTạo bảng thuốc cá nhân: Ghi lại tên thuốc, công dụng, liều dùng, thời gian uống trong ngày và lưu ý khi sử dụng (trước/sau ăn, tránh kết hợp với thực phẩm gì…).\n" +
                        "\t2.\tSử dụng hộp chia thuốc theo ngày/giờ: Giúp bạn dễ dàng nhận biết đã uống hay chưa và giảm nguy cơ quên liều.\n" +
                        "\t3.\tDùng ứng dụng nhắc nhở thuốc: Có nhiều app miễn phí trên smartphone giúp bạn đặt lịch uống thuốc, ghi chú phản ứng phụ và nhắc lịch tái khám.\n" +
                        "\t4.\tMang theo danh sách thuốc khi đi khám: Bác sĩ sẽ dựa vào đó để điều chỉnh toa phù hợp, tránh trùng lặp hoặc phản ứng chéo.\n" +
                        "\n" +
                        "Lưu ý quan trọng:\n" +
                        "\t•\tKhông tự ý ngưng thuốc kể cả khi đã thấy đỡ – điều này dễ khiến bệnh tái phát hoặc trở nặng.\n" +
                        "\t•\tKhông dùng thuốc theo lời mách bảo khi chưa được bác sĩ kê toa, kể cả từ người thân hay mạng xã hội.\n" +
                        "\t•\tĐọc kỹ hướng dẫn sử dụng kèm theo thuốc và hỏi lại nhân viên y tế nếu có điều gì không hiểu.\n" +
                        "\n" +
                        "Theo dõi thuốc không chỉ giúp bạn làm chủ quá trình điều trị, mà còn thể hiện sự chủ động chăm sóc sức khỏe bản thân. Đó là một phần quan trọng trong lối sống lành mạnh và thông thái.",
                R.drawable.thuoc));

        filteredArticleList.addAll(articleList);
    }


    private void setupRecyclerView() {
        articleAdapter = new ArticleAdapter(this, filteredArticleList, article -> {
            Intent intent = new Intent(MainActivity.this, ArticleDetailActivity.class);
            intent.putExtra("title", article.getTitle());
            intent.putExtra("description", article.getDescription());
            intent.putExtra("content", article.getContent());
            startActivity(intent);
        });

        articleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        articleRecyclerView.setAdapter(articleAdapter);
    }

    private void setupSearchFunction() {
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterArticles(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                String query = searchBar.getText().toString().trim();
                if (!query.isEmpty()) {
                    filterArticles(query);
                } else {
                    Toast.makeText(this, "Vui lòng nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });
    }

    private void setupCalendarView() {
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        int savedYear = pref.getInt("year", 0);
        if (savedYear != 0) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, savedYear);
            cal.set(Calendar.MONTH, pref.getInt("month", 0));
            cal.set(Calendar.DAY_OF_MONTH, pref.getInt("dayOfMonth", 0));
            calendarView.setDate(cal.getTimeInMillis());
        }

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            fileName = String.format("%02d_%02d_%04d", dayOfMonth, month + 1, year);
            saveDateToPreferences(year, month, dayOfMonth);
        });
    }

    private void saveNoteForSelectedDate() {
        if (fileName.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn ngày trước khi ghi chú", Toast.LENGTH_SHORT).show();
            return;
        }

        String noteContent = noteEditText.getText().toString();
        try (FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE)) {
            fos.write(noteContent.getBytes());
            Toast.makeText(this, "Đã lưu ghi chú", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Lỗi khi lưu ghi chú", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveDateToPreferences(int year, int month, int dayOfMonth) {
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putInt("year", year);
        editor.putInt("month", month);
        editor.putInt("dayOfMonth", dayOfMonth);
        editor.apply();
    }

    private void setupNavigation() {
        navHome.setOnClickListener(v -> Toast.makeText(this, "Đang ở trang chủ", Toast.LENGTH_SHORT).show());
        navProfile.setOnClickListener(v -> startActivity(new Intent(this, SecondActivity.class)));
        navSettings.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));

//ấn mở khẩn cấp
        navHospital.setOnClickListener(v -> startActivity(new Intent(this, EmergencyActivity.class)));

    }

    private void filterArticles(String query) {
        filteredArticleList.clear();
        for (Article article : articleList) {
            if (article.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    article.getDescription().toLowerCase().contains(query.toLowerCase()) ||
                    article.getContent().toLowerCase().contains(query.toLowerCase())) {
                filteredArticleList.add(article);
            }
        }
        articleAdapter.updateArticleList(filteredArticleList);
    }

    private void startHealthTipRotation() {
        tipHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentTipIndex = (currentTipIndex + 1) % healthTips.length;
                summaryTitle.animate()
                        .alpha(0f)
                        .translationY(-30)
                        .setDuration(500)
                        .withEndAction(() -> {
                            summaryTitle.setText(healthTips[currentTipIndex]);
                            summaryTitle.setAlpha(0f);
                            summaryTitle.setTranslationY(30);
                            summaryTitle.animate()
                                    .alpha(1f)
                                    .translationY(0)
                                    .setDuration(500)
                                    .start();
                        }).start();
                tipHandler.postDelayed(this, 10000);
            }
        }, 10000);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase, LocaleHelper.getLanguage(newBase)));
    }
}