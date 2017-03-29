package ds.serv.bubo.lombok;

/**
 * LombokDemo
 *
 * @author Leslie
 * @since 2016/12/13
 */
public class LombokDemo {
    public static void main(String[] args) {
        ConciseBean bean = new ConciseBean();
        bean.setA(20);
        bean.setB(" days per person");
        System.out.println(bean);
    }
}
