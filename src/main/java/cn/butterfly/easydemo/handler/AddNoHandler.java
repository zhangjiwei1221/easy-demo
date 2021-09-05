package cn.butterfly.easydemo.handler;

import cn.butterfly.easydemo.constant.ExcelConstant;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.write.handler.AbstractRowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.property.ExcelWriteHeadProperty;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * 自定义 excel 行处理器, 增加序号列
 *
 * @author zjw
 * @date 2020-09-05
 */
@Component
public class AddNoHandler extends AbstractRowWriteHandler {

	private boolean init = true;

	@Override
	public void beforeRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Integer rowIndex,
								Integer relativeRowIndex, Boolean isHead) {
		if (init) {
			// 修改存储头部及对应字段信息的 map, 将其中的内容均右移一位, 给新增的序列号预留为第一列
			ExcelWriteHeadProperty excelWriteHeadProperty = writeSheetHolder.excelWriteHeadProperty();
			Map<Integer, Head> headMap = excelWriteHeadProperty.getHeadMap();
			Map<Integer, ExcelContentProperty> contentMap = excelWriteHeadProperty.getContentPropertyMap();
			int size = headMap.size();
			for (int current = size; current > 0; current--) {
				int previous = current - 1;
				headMap.put(current, headMap.get(previous));
				contentMap.put(current, contentMap.get(previous));
			}
			// 空出第一列
			headMap.remove(0);
			contentMap.remove(0);
			// 只需要修改一次 map 即可, 故使用 init 变量进行控制
			init = false;
		}
	}

	@Override
	public void afterRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
							   Integer relativeRowIndex, Boolean isHead) {
		// 在行创建完成后添加序号列
		Cell cell = row.createCell(0);
		int rowNum = row.getRowNum();
		if (rowNum == 0) {
			cell.setCellValue(ExcelConstant.TITLE);
		} else {
			cell.setCellValue(rowNum);
		}
	}

	@Override
	public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
								Integer relativeRowIndex, Boolean isHead) {
		if (row.getLastCellNum() > 1) {
			// 将自定义新增的序号列的样式设置与默认的样式一致
			row.getCell(0).setCellStyle(row.getCell(1).getCellStyle());
		}
	}

}
