package dev.josemc.nightcore.test.files;

import dev.josemc.nightcore.files.annotations.Comment;
import dev.josemc.nightcore.files.annotations.File;
import dev.josemc.nightcore.files.annotations.Path;
import dev.josemc.nightcore.files.annotations.Version;

@File("settings.conf")
@Version(3)
public class ExampleHolder {

    @Path("hello")
    public static String HELLO = "Hola";

    @Path("hello_comment")
    @Comment("Comentario de prueba")
    public static String HELLO_COMMENT = "Hola con comentario";

    @Path("hello_comment_array")
    @Comment({"Linea 1", "Linea 2"})
    public static String HELLO_COMMENT_ARRAY = "Hola con comentario";

    @Path("Section1.hola")
    public static int FROM_SECTION = 1;

    @Path({"Section1", "array"})
    public static int ARRAY_PATH = 2;

    @Path({"autoupdate"})
    public static boolean UPDATE = true;

    @Path({"autoupdate2"})
    public static boolean UPDATE2 = true;
}



