package tr.com.obss.jip.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Helper {
    public static Pageable getPagable(Integer page, Integer pageSize, String field, String order) {
        Pageable pageable;
        if (order.equals("descend")) {
            pageable = PageRequest.of(page - 1, pageSize, Sort.by(field).descending());
        } else {
            pageable = PageRequest.of(page - 1, pageSize, Sort.by(field).ascending());
        }

        return pageable;
    }
}
