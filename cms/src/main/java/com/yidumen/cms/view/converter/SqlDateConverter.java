package com.yidumen.cms.view.converter;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@FacesConverter("sqlDate")
public final class SqlDateConverter implements Converter {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        try {
            return new Date(dateFormat.parse(string).getTime());
        } catch (ParseException ex) {
            final FacesMessage message = new FacesMessage();
            message.setSummary("日期格式错误");
            message.setDetail("不符合日期格式要求，格式应该为：年-月-日");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(message, ex);
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o instanceof Date) {
                return dateFormat.format(o);
        } else {
            final FacesMessage message = new FacesMessage();
            message.setSummary("对象类型错误");
            message.setDetail("对象类型应该为java.sql.Date");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(message);
            
        }
    }

}
