package cn.butterfly.easydemo.controller;

import cn.butterfly.easydemo.constant.ExcelConstant;
import cn.butterfly.easydemo.entity.Student;
import cn.butterfly.easydemo.handler.AddNoHandler;
import com.alibaba.excel.EasyExcel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * Excel 下载控制器
 *
 * @author zjw
 * @date 2021-09-05
 */
@RestController
public class ExcelController {

    /**
     * 添加序号列测试
     *
	 * @param response response
     */
    @GetMapping("/col")
    public void col(HttpServletResponse response) {
        try {
            List<Student> students = getStudentList();
            EasyExcel.write(response.getOutputStream(), Student.class)
                    .registerWriteHandler(new AddNoHandler())
                    .sheet()
                    .doWrite(students);
        } catch (Exception e) {
            System.out.println(ExcelConstant.DOWNLOAD_FAILED);
        }
    }

    /**
     * 生成学生列表
     *
     * @return 学生列表
     */
    private List<Student> getStudentList() {
        return Arrays.asList(
                new Student("2021090101", "张三", 19),
                new Student("2021090102", "李四", 18),
                new Student("2021090103", "王二", 20)
        );
    }

}
