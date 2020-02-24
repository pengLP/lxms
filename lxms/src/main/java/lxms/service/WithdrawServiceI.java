package lxms.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import lxms.entity.Withdraw;
import lxms.tool.PageFilter;

public interface WithdrawServiceI {

	public List<Withdraw> listNotHandler(PageFilter pageFilter);

	public List<Withdraw> listHandler(PageFilter pageFilter);

	public Workbook export();

	public void updateSuccess(Long withdrawId);

	public void updateFalse(Long withdrawId);

	public void uploadAdd(InputStream inputStream) throws IOException;
}
