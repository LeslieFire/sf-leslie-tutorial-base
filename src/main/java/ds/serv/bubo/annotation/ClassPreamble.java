package ds.serv.bubo.annotation;

import java.lang.annotation.Documented;

/**
 * Author: Leslie
 * Date: 29/8/2016.
 */

@Documented
public @interface ClassPreamble {

    String author();
    String date();
    int currentRevision() default 1;
    String lastModified() default "N/A";
    String lastModifiedBy() default "N/A";

    // Note use of array
    String[] reviewers();
}
