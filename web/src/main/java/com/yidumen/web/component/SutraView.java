package com.yidumen.web.component;

import com.yidumen.dao.entity.Sutra;
import com.yidumen.web.service.impl.SutraServiceImpl;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * 组件sutraView的后台bean
 *
 * @author 蔡迪旻
 */
@Named("sutraView")
public final class SutraView {

    @Inject
    private SutraServiceImpl service;
    private Sutra previous;
    private Sutra next;

    public int getPageCount(final Sutra sutra) {
        return service.getPageCount(sutra);
    }

    public Sutra getPrevious(final Sutra sutra) {
        if (previous == null) {
            previous = service.findPreSutra(sutra);
        }
        return previous;
    }

    public Sutra getNext(final Sutra sutra) {
        if (next == null) {
            next = service.findNextSutra(sutra);
        }
        return next;
    }

    public String getShotTitle(final String title) {
        return title.length() > 12 ? title.substring(0, 12) + "......" : title;
    }
}
