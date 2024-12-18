package org.example;

import io.growing.collector.codec.CodecUtil;
import io.growing.collector.codec.CompressCodec;
import io.growing.collector.utils.Bytes;
import org.junit.Test;

import java.io.DataInputStream;

public class CollectTest {

    String str1 = "{\"foo\":\"bar\"}";
    String str2 = "6<86><F0>D\\u0000<E0><86>\\u000E`<A6>\\u0006P\\u0005<81><EC>\\u000E<E0>\\u0015\\u0002X\\u0016<CE>\\u0006p\\u0005<CA>\\u001C#\\u0000.\\u0001\\u0018\\u0007`\\u0019<80>\\u0016z\\u0002b`\\u0006\\u00019<E8>\\u0003<8E><80>hÀ\\r<CE>\\u0000;B\\u0018\\u0002xC<81>L\\u00005\\u0000<92>\\u0001D\\u0003<A8>\\u0007<D0>\\f \\u0006AF<80><D2>a<F8>\\u00012\\u0015<80>1<9C>\\u0005Gf֠\\r<8C><C0>#(<8E>\\u0002<B0>\\u0005<A2><EB>@\\u0019<BD>w<F4><A1><E8><<B9><BD>(<FC><99>̠<ED>\\u001C<BC><B8><8D>\\u001D<BD>\\f<C1><F0>\\t<F0><B0>PD<AD>e<9D><A9>\\u001DX<A1>Y<A9><DD>X<8D>]i<FC><99>\\u001D<D9><DD>\\u001C<9D>Y<DC><CD>ؘ<BD>Y\\\\<E1>(<AB>ْ<8C><A1><88>\\u0010P\\u0001\\\\\\u0000<9C>-<B3><C8><C0><B8><B8><CD>X]]]<AA>샓\\tp\\t<89>H(h\\u0019<98><D9><D8><E9><D9>]<8C>Pp<A0><B0>Edp\\u0001<AD>\\t\\b<B6>\\u0000\n" +
            "<E9>\\u001C<B6><D0><E1>Q\\u001E\\u0000l<9E>D<E1>\\t<92><D0>B\\u0012\\u0016@\\u0007<A2>B<82><A0>F\\u001C\\u0015<D4>\\u0015<F4>\\u0019<99>n\\u0000\\u0011a<94>\\r\\u0007\\n<C1>\\u0011\\u0001_><B7><85>\\f1<C2><C9>ގd<BE>\\f<CC>3<82><88>\\u0000\\u0012p,\\f\\t\\u0000\\u000F!qV<FC>2E4B<A2><C1>\\u0019<81><BB>r<9D><95><94>e<B9><C8><E0><C3>t<A6>F<C4><F5><A1><<D8><C9>\\u001CH<86>\\b5<80><C8>&\\u0000/$#Z<EF><U+0080>@ <C2><D1>FZ<E1>4<A1>=X&<E4><A0><D5>,7\\u0019<81>(\\f.\\u0007\\u0016<85><C1><A3>$`\\u0019km<BB><80><EA>wQ<D6>XB\\u0017<D9>V\\u0004\\u0001c<CA>\\u00004U\\u0000<85>ހ0\\u0017@7Ϡ\\u001Fo<D9>!N<F0><8B>Q_\\u0000\\u0002<92><B6>@<CC> A<F0><E4>Ph.<E0><F6>z<BC><88><EF>O<9C>\\u0007<E7><F4>#<83>!<D0><D8>E(ƜG<FC>\\u0000J<97>\\u0011($<CC>B<C1>}A<F5><C6><ED>Ŷ<D8>\\u0002\\t\\u0018<8C>\\u0000~h<\\u0012M \\u0002<F1>B\n" +
            "<8C>\\u00002G\\u0005<AA>斝<C0>g<94>%<CA><EF><EF><87><C0>\\u0000<E4>H\\u001B<80>)\\u0012<9E><8C>x\\u0001\\bi<8F><EC>.)<FD><8C>^?<B6>>\\u001FJ\\u0007<D7>\\u0015<C3>zb<9E>?\\u0017<A1><CD><FA><80><B8><E2><AF>\\u0017θδ2@\\u0000y\\u0002 <84><CA>\\t<E2>\\u0004<93><CE>[<B8>\n" +
            "<F0>N\\b<85>|<C8>~#<83><B8><EE>\\u0015<C5><F2><AE>\\u001D<96>\\b!<<A9>\\u0014\\n3<AA>(mB<80>A\\u0004Q\\u0012E<91>\\u0018A.<E3>\\ft<83>*\\u000B.\\u000F&F<86>ԃ\\u0007\\u0012!q<EC>a\\t<93>a\\u00103\\u0011s\\f\\u0012\\u0013ɋ<B8>\\u0010\\u000E%s\\u0011p)\\u0016c<91><98>S\\u0012<C6>\n" +
            "\\u0010<B5>!\\u000E<DB><E0><D0>&<A6>\\u0000<C0>_\\n\\f<E1>|\\b\\u001C\\u0000\\u0002:\\f<A2>\\u0018<CD>aP^?\\u0004\\\"<88><84>\\t<9E>fY<96>5<96>\\u0012<D0>\\u0000/<80>\\u000B<A4>\\u0000\\u0000";

    @Test
    public void test1() {
        String stm = "1734433148829";
        System.out.println(CodecUtil.convertCodec(3, 1, 0, stm));
    }
}
