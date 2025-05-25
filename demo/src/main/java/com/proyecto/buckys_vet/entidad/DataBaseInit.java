package com.proyecto.buckys_vet.entidad;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.buckys_vet.repositorio.DuenoRepositorio;
import com.proyecto.buckys_vet.repositorio.MascotaRepositorio;
import com.proyecto.buckys_vet.repositorio.MedicamentoRepositorio;
import com.proyecto.buckys_vet.repositorio.TratamientoRepositorio;
import com.proyecto.buckys_vet.repositorio.VeterinarioRepositorio;

@Controller
@Transactional
public class DataBaseInit implements ApplicationRunner {
        @Autowired
        DuenoRepositorio duenoRepositorio;
        @Autowired
        MascotaRepositorio mascotaRepositorio;

        @Autowired
        VeterinarioRepositorio veterinarioRepositorio;

        @Autowired
        TratamientoRepositorio tratamientoRepositorio;

        @Autowired
        MedicamentoRepositorio medicamentoRepositorio;

        private static final boolean GENERAR_DATOS = true;

        private static final String[] NOMBRES_MUJERES = {
                        "María", "Ana", "Luisa", "Marta", "Elena", "Lucía", "Gabriela", "Valentina", "Diana", "Rosa",
                        "Daniela", "Mariana", "Carla"
        };

        private static final String[] NOMBRES_HOMBRES = {
                        "Carlos", "Luis", "Jorge", "Roberto", "Pablo", "Andrés", "Francisco", "Esteban", "Fernando",
                        "Ricardo",
                        "Emilio", "Alejandro", "Javier", "Tomás"
        };

        private static final String[] APELLIDOS = {
                        "Gómez", "Pérez", "Fernández", "López", "Martínez", "Gutiérrez", "Rodríguez", "Vargas",
                        "Castro", "Torres",
                        "Ramírez", "Muñoz", "Ortega", "Moreno", "Salazar", "Núñez", "Mendoza", "Herrera", "Cabrera",
                        "Santana",
                        "Chávez", "Reyes", "Benítez", "Esquivel", "Navarro", "Fuentes", "Ávila", "Mejía", "Soto",
                        "Acosta"
        };

        private static final String[] NOMBRES_MASCOTAS = {
                        "Luna", "Simba", "Coco", "Rocky", "Bella", "Max", "Milo", "Toby", "Rex", "Nala",
                        "Kira", "Loki", "Bruno", "Daisy", "Chester", "Sasha", "Thor", "Boby", "Zeus", "Canela",
                        "Maya", "Apolo", "Tina", "Bobby", "Fiona", "Terry", "Romeo", "Mimi", "Odin", "Leia"
        };

        private static final String[] ESPECIES = { "Perro", "Gato" };

        private static final String[] ENFERMEDADES = {
                        "Ninguna", "Alergia alimentaria", "Parvovirus", "Displasia de cadera", "Artrosis",
                        "Infección en el oído",
                        "Moquillo canino", "Insuficiencia renal", "Hipotiroidismo", "Leishmaniasis", "Gastroenteritis",
                        "Epilepsia",
                        "Otitis", "Diabetes", "Anemia", "Tumor mamario", "Pancreatitis", "Obesidad", "Leucemia felina",
                        "Rabia"
        };

        private static final String[] IMAGENES_PERROS = {
                        "https://cdn.pixabay.com/photo/2023/08/18/15/02/dog-8198719_1280.jpg",
                        "https://cdn.pixabay.com/photo/2020/11/22/20/12/schafer-dog-5767834_1280.jpg",
                        "https://cdn.pixabay.com/photo/2015/11/17/13/13/bulldog-1047518_1280.jpg",
                        "https://cdn.pixabay.com/photo/2016/11/29/11/26/dog-1869167_1280.jpg",
                        "https://cdn.pixabay.com/photo/2017/03/27/13/23/dog-2178696_1280.jpg",
                        "https://cdn.pixabay.com/photo/2016/12/13/05/15/puppy-1903313_1280.jpg",
                        "https://cdn.pixabay.com/photo/2016/07/15/15/55/dachshund-1519374_1280.jpg",
                        "https://cdn.pixabay.com/photo/2016/10/26/22/02/dog-1772759_1280.jpg",
                        "https://cdn.pixabay.com/photo/2016/02/11/17/00/dog-1194087_1280.jpg",
                        "https://cdn.pixabay.com/photo/2016/11/23/18/06/dog-1854119_1280.jpg",
                        "https://cdn.pixabay.com/photo/2018/05/07/10/48/husky-3380548_1280.jpg",
                        "https://cdn.pixabay.com/photo/2018/03/31/06/31/dog-3277414_1280.jpg",
                        "https://cdn.pixabay.com/photo/2016/10/15/12/01/dog-1742295_1280.jpg",
                        "https://cdn.pixabay.com/photo/2014/03/14/20/13/dog-287420_1280.jpg",
                        "https://cdn.pixabay.com/photo/2017/07/31/21/15/dog-2561134_1280.jpg",
                        "https://cdn.pixabay.com/photo/2014/12/10/05/50/english-bulldog-562723_1280.jpg",
                        "https://cdn.pixabay.com/photo/2018/03/18/18/06/australian-shepherd-3237735_1280.jpg",
                        "https://cdn.pixabay.com/photo/2017/02/01/09/48/jack-russell-2029214_1280.jpg",
                        "https://cdn.pixabay.com/photo/2021/07/17/23/01/dog-6474269_1280.jpg",
                        "https://cdn.pixabay.com/photo/2019/05/27/19/08/puppy-4233378_1280.jpg",
                        "https://cdn.pixabay.com/photo/2021/01/21/16/17/english-cocker-spaniel-5937757_1280.jpg",
                        "https://cdn.pixabay.com/photo/2019/06/05/08/37/dog-4253238_1280.jpg",
                        "https://cdn.pixabay.com/photo/2021/04/07/05/39/labrador-retriever-6158095_1280.jpg",
                        "https://cdn.pixabay.com/photo/2021/08/18/22/42/australian-shepherd-6556697_1280.jpg",
                        "https://cdn.pixabay.com/photo/2022/07/09/17/42/dog-7311407_1280.jpg",
                        "https://cdn.pixabay.com/photo/2015/06/24/13/32/dog-820014_1280.jpg",
                        "https://cdn.pixabay.com/photo/2020/11/08/10/25/dog-5723334_1280.jpg",
                        "https://cdn.pixabay.com/photo/2021/07/05/14/07/dog-6389277_1280.jpg",
                        "https://cdn.pixabay.com/photo/2017/06/24/09/13/continental-bulldog-2437110_1280.jpg",
                        "https://cdn.pixabay.com/photo/2018/05/09/16/15/dog-3385541_1280.jpg",
                        "https://cdn.pixabay.com/photo/2020/05/03/13/09/puppy-5124947_1280.jpg",
                        "https://cdn.pixabay.com/photo/2015/05/24/22/33/german-longhaired-pointer-782498_1280.jpg",
                        "https://cdn.pixabay.com/photo/2016/11/29/09/58/dog-1868871_1280.jpg",
                        "https://cdn.pixabay.com/photo/2022/01/17/19/59/dog-6945696_1280.jpg",
                        "https://cdn.pixabay.com/photo/2021/08/21/19/39/greyhound-6563435_1280.jpg",
                        "https://cdn.pixabay.com/photo/2022/03/30/11/12/dog-7101015_1280.jpg",
                        "https://cdn.pixabay.com/photo/2015/05/13/08/07/dalmatians-765138_1280.jpg",
                        "https://cdn.pixabay.com/photo/2023/03/18/17/48/basset-hound-7861037_1280.jpg",
                        "https://cdn.pixabay.com/photo/2016/11/29/01/24/dog-1866530_1280.jpg",
                        "https://cdn.pixabay.com/photo/2023/04/28/14/35/dog-7956828_1280.jpg",
                        "https://cdn.pixabay.com/photo/2023/10/01/12/27/border-collie-8287329_1280.jpg",
                        "https://cdn.pixabay.com/photo/2019/08/07/14/10/golden-retriever-4390884_1280.jpg",
                        "https://cdn.pixabay.com/photo/2022/02/06/14/06/dog-6997211_1280.jpg",
                        "https://cdn.pixabay.com/photo/2020/11/20/16/26/labrador-5762115_1280.jpg",
                        "https://cdn.pixabay.com/photo/2017/05/09/23/02/dog-2299482_1280.jpg",
                        "https://cdn.pixabay.com/photo/2017/09/25/13/11/dog-2785066_1280.jpg",
                        "https://cdn.pixabay.com/photo/2023/03/02/14/46/pit-bull-7825554_1280.jpg",
                        "https://cdn.pixabay.com/photo/2024/02/05/16/23/labrador-8554882_1280.jpg",
                        "https://cdn.pixabay.com/photo/2023/04/15/17/08/bernese-mountain-dog-7928156_1280.jpg",
                        "https://cdn.pixabay.com/photo/2022/04/12/19/35/dog-7128749_1280.jpg"
        };

        private static final String[] IMAGENES_GATOS = {
                        "https://cdn.pixabay.com/photo/2023/08/18/01/32/cat-8197577_1280.jpg",
                        "https://cdn.pixabay.com/photo/2022/06/19/04/25/cat-7271017_1280.jpg",
                        "https://cdn.pixabay.com/photo/2016/01/20/13/05/cat-1151519_1280.jpg",
                        "https://cdn.pixabay.com/photo/2014/11/30/14/11/cat-551554_1280.jpg",
                        "https://cdn.pixabay.com/photo/2018/10/11/12/31/cat-3739702_1280.jpg",
                        "https://cdn.pixabay.com/photo/2023/09/21/17/05/european-shorthair-8267220_1280.jpg",
                        "https://cdn.pixabay.com/photo/2014/10/01/10/46/cat-468232_1280.jpg",
                        "https://cdn.pixabay.com/photo/2022/07/03/22/00/cat-7300029_1280.jpg",
                        "https://cdn.pixabay.com/photo/2020/08/17/18/38/cat-5496162_1280.jpg",
                        "https://cdn.pixabay.com/photo/2012/10/12/17/12/cat-61079_1280.jpg",
                        "https://cdn.pixabay.com/photo/2020/05/04/14/14/cat-5129332_1280.jpg",
                        "https://cdn.pixabay.com/photo/2014/03/29/09/17/cat-300572_1280.jpg",
                        "https://cdn.pixabay.com/photo/2017/07/22/15/21/cat-2528935_1280.jpg",
                        "https://cdn.pixabay.com/photo/2024/03/07/10/38/simba-8618301_1280.jpg",
                        "https://cdn.pixabay.com/photo/2018/05/14/21/43/british-shorthair-3401683_1280.jpg",
                        "https://cdn.pixabay.com/photo/2013/05/17/15/54/cat-111793_1280.jpg",
                        "https://cdn.pixabay.com/photo/2017/03/14/14/49/cat-2143332_1280.jpg",
                        "https://cdn.pixabay.com/photo/2018/04/20/17/18/cat-3336579_1280.jpg",
                        "https://cdn.pixabay.com/photo/2020/09/17/13/59/cat-5579221_1280.jpg",
                        "https://cdn.pixabay.com/photo/2020/11/26/11/48/cat-5778777_1280.jpg",
                        "https://cdn.pixabay.com/photo/2021/06/04/14/14/cat-6309964_1280.jpg",
                        "https://cdn.pixabay.com/photo/2022/03/27/11/23/cat-7094808_1280.jpg",
                        "https://cdn.pixabay.com/photo/2018/07/08/14/16/cat-3523992_1280.jpg",
                        "https://cdn.pixabay.com/photo/2014/06/03/01/31/cat-360807_1280.jpg",
                        "https://cdn.pixabay.com/photo/2019/06/12/15/07/cat-4269479_1280.jpg",
                        "https://cdn.pixabay.com/photo/2018/08/08/05/12/cat-3591348_1280.jpg",
                        "https://cdn.pixabay.com/photo/2021/11/02/10/46/cat-6762936_1280.jpg",
                        "https://cdn.pixabay.com/photo/2018/06/28/14/12/cat-3504008_1280.jpg",
                        "https://cdn.pixabay.com/photo/2016/11/18/21/57/animal-1837067_1280.jpg",
                        "https://cdn.pixabay.com/photo/2019/06/08/17/02/cat-4260536_1280.jpg",
                        "https://cdn.pixabay.com/photo/2017/08/07/12/27/cat-2603300_1280.jpg",
                        "https://cdn.pixabay.com/photo/2021/12/01/18/17/cat-6838844_1280.jpg",
                        "https://cdn.pixabay.com/photo/2015/11/16/22/14/cat-1046544_1280.jpg",
                        "https://cdn.pixabay.com/photo/2020/11/25/03/04/russian-blue-cat-5774414_1280.jpg",
                        "https://cdn.pixabay.com/photo/2020/11/22/17/28/cat-5767334_1280.jpg",
                        "https://cdn.pixabay.com/photo/2019/12/17/16/06/cat-4701934_1280.jpg",
                        "https://cdn.pixabay.com/photo/2012/11/26/13/58/cat-67345_1280.jpg",
                        "https://cdn.pixabay.com/photo/2015/01/31/12/36/cat-618470_1280.jpg",
                        "https://cdn.pixabay.com/photo/2018/01/14/14/42/cat-3081939_1280.jpg",
                        "https://cdn.pixabay.com/photo/2021/07/13/11/34/cat-6463284_1280.jpg",
                        "https://cdn.pixabay.com/photo/2023/06/29/12/28/cats-8096304_1280.jpg",
                        "https://cdn.pixabay.com/photo/2016/11/19/21/09/cat-1841202_1280.jpg",
                        "https://cdn.pixabay.com/photo/2023/04/07/07/14/cat-7905702_1280.jpg",
                        "https://cdn.pixabay.com/photo/2023/12/08/05/38/cat-8436843_1280.jpg",
                        "https://cdn.pixabay.com/photo/2023/05/19/19/43/cat-8005275_1280.jpg",
                        "https://cdn.pixabay.com/photo/2021/07/15/10/47/cat-6468112_1280.jpg",
                        "https://cdn.pixabay.com/photo/2021/10/27/19/09/cat-6748193_1280.jpg",
                        "https://cdn.pixabay.com/photo/2015/11/15/20/21/cat-1044750_1280.jpg",
                        "https://cdn.pixabay.com/photo/2023/03/09/20/02/cat-7840767_1280.jpg",
                        "https://cdn.pixabay.com/photo/2024/02/28/07/42/european-shorthair-8601492_1280.jpg"
        };

        private static final String[] IMAGENES_DUENOS_MUJERES = {
                        "https://media.istockphoto.com/id/2099403180/es/foto/mujer-de-negocios-risue%C3%B1a-de-pie-con-los-brazos-cruzados-contra-la-pared-de-una-oficina.jpg?s=612x612&w=0&k=20&c=SmPSzyS-h8Q7MVZt1bi5IeJbjJTav4dSyOBH9P8aXoc=",
                        "https://media.istockphoto.com/id/1398385367/es/foto/feliz-mujer-de-negocios-millennial-con-gafas-posando-con-las-manos-cruzadas.jpg?s=612x612&w=0&k=20&c=pgJoHs698wY3npJz7AyxosicITYgSDo0G6fxbT5Bwa0=",
                        "https://media.istockphoto.com/id/1369508766/es/foto/hermosa-mujer-latina-exitosa-sonriendo.jpg?s=612x612&w=0&k=20&c=f-3MdwiVjpE4UWQdqLC3vpWViYMCiGUPr5aKLCmTnDI=",
                        "https://media.istockphoto.com/id/1401557224/es/foto/mujer-de-negocios-segura-de-s%C3%AD-misma-en-la-oficina-moderna.jpg?s=612x612&w=0&k=20&c=uNw2KkZ8D8-FDubjiAPGMSE7Zl1ZcQ6gXvCau5WVAP4=",
                        "https://media.istockphoto.com/id/1591926305/es/foto/retrato-de-una-mujer-profesional-con-traje-mujer-de-negocios-parada-en-una-oficina.jpg?s=612x612&w=0&k=20&c=n-TqAXJHizp3tJgFXQ3cQcN4mOnnLF4zg848tHx2tFA=",
                        "https://media.istockphoto.com/id/1308882664/es/foto/retrato-de-estudio-de-una-joven-empresaria-seria-con-los-brazos-cruzados-contra-el-fondo-llano.jpg?s=612x612&w=0&k=20&c=n-md4iRpTAFgN2Bdl3ZUX-hYrYuVPlr9bqOxNOVM_UM=",
                        "https://media.istockphoto.com/id/1326211139/es/foto/retrato-de-una-joven-empresaria-segura-de-s%C3%AD-misma-que-trabaja-en-una-oficina-moderna.jpg?s=612x612&w=0&k=20&c=APIaa2oSg2jcvp9CpiSd6W4E-JzZip3panI6mU-JQ00=",
                        "https://media.istockphoto.com/id/2150868016/es/foto/retrato-de-una-sonriente-mujer-de-negocios-con-una-tableta-digital-de-pie-en-la-oficina.jpg?s=612x612&w=0&k=20&c=EuCuhzhDY82cvWMEp3sDNKQhlrfpHTVDIlY6PmhBIMQ=",
                        "https://media.istockphoto.com/id/1987655119/es/foto/smiling-young-businesswoman-standing-in-the-corridor-of-an-office.jpg?s=612x612&w=0&k=20&c=BSgEl7Udve77L_J2gjA2LytCTvy_GxVYUX9-i9dVQpM=",
                        "https://media.istockphoto.com/id/1281083606/es/foto/foto-de-atractiva-se%C3%B1ora-encantadora-lindo-peludo-brazos-cruzados-persona-segura-de-s%C3%AD-mismo.jpg?s=612x612&w=0&k=20&c=H1UrO3iS28lOLOq8AOiVdwHb4vNkNSEKhK9qwZlXVTs=",
                        "https://media.istockphoto.com/id/1593909818/es/foto/feliz-mujer-de-negocios-segura-en-la-oficina-mirando-a-la-c%C3%A1mara.jpg?s=612x612&w=0&k=20&c=M6OaUaJk_u8yfPp1JLg9roj51WUC4gIYbILANueNol0=",
                        "https://media.istockphoto.com/id/1355515310/es/foto/mujer-de-negocios-exitosa-usando-una-tableta-en-la-oficina.jpg?s=612x612&w=0&k=20&c=qhi9kEnf2lA1e4aE3faLlNFo3Yzcwo4VqsgEv3nuxMg=",
                        "https://media.istockphoto.com/id/1386866479/es/foto/sonriente-y-segura-de-s%C3%AD-misma-joven-empresaria-auditora-escribiendo-en-el-portapapeles.jpg?s=612x612&w=0&k=20&c=Tq6wZfFTahIJzSkTOSVITwmo6yi0htrStd3UbknfxHs=",
                        "https://media.istockphoto.com/id/1629541271/es/foto/sonrisa-brazos-cruzados-y-retrato-de-una-mujer-en-el-trabajo-por-orgullo-empresarial-y.jpg?s=612x612&w=0&k=20&c=zdWT_vEn-J3j-IaEceKWxjyZjDPE4VrseUPf5C_CijA=",
                        "https://media.istockphoto.com/id/1496615448/es/foto/retrato-de-hermosa-ni%C3%B1a-hispana-aislada-sobre-fondo-gris.jpg?s=612x612&w=0&k=20&c=OBtiXAf4SzTgmn2UrfvR6jk6Tuu175QBexxUww1IQck=",
                        "https://media.istockphoto.com/id/1915382108/es/foto/gerente-de-la-dama-cauc%C3%A1sica-milenaria-confiada-y-amigable-sonriente-maestra-en-ropa-formal.jpg?s=612x612&w=0&k=20&c=XzkMX37ma5sOsY7MyjFrFPTCtBceOx2yjjFyiOAxfuU=",
                        "https://media.istockphoto.com/id/1487508210/es/foto/retrato-de-una-joven-segura-de-s%C3%AD-misma-de-pie-en-el-espacio-de-los-creadores.jpg?s=612x612&w=0&k=20&c=UkbXRrpage0mY8ZlqB_P8dHvNJs1aE4Yf83_NaZ-uP0=",
                        "https://media.istockphoto.com/id/1289220949/es/foto/mujer-sonriente-exitosa-con-gafas-en-la-pared-gris.jpg?s=612x612&w=0&k=20&c=vH5cbmaJoJHJ_BWv9IBF1wri-4GwbhGrmJVcGcEhkCk=",
                        "https://media.istockphoto.com/id/1365606632/es/foto/foto-de-una-joven-empresaria-usando-una-tableta-digital-mientras-est%C3%A1-en-el-trabajo.jpg?s=612x612&w=0&k=20&c=2KzjQfY7LZ6cYujFuJhokbySJJBPI0H4jkvlXLy1oZQ=",
                        "https://media.istockphoto.com/id/1317889905/es/foto/foto-de-una-joven-empresaria-usando-una-tableta-digital-en-una-oficina-moderna.jpg?s=612x612&w=0&k=20&c=YKDXaqXhRZAgyP7KknWLZYOiwZZbiK8c2uqWYLfqlfU=",
                        "https://media.istockphoto.com/id/961097602/es/foto/mujer-sonriente-de-pie-con-los-brazos-cruzados.jpg?s=612x612&w=0&k=20&c=pLrO_piMLY_6-6HANvQWioR1DakFKe3QH0Yq5QieNoM=",
                        "https://media.istockphoto.com/id/1411012107/es/foto/mujer-de-negocios-madura-sonriendo-mientras-est%C3%A1-de-pie-con-los-brazos-cruzados-en-el-trabajo.jpg?s=612x612&w=0&k=20&c=F0wMRxe1ZlkUfhSVgpfDU7Uldx7Tv8TY7XlJm2ZUYNY="
        };

        private static final String[] IMAGENES_DUENOS_HOMBRES = {
                        "https://media.istockphoto.com/id/1364917563/es/foto/hombre-de-negocios-sonriendo-con-los-brazos-cruzados-sobre-fondo-blanco.jpg?s=612x612&w=0&k=20&c=NqMHLF8T4RzPaBE_WMnflSGB_1-kZZTQgAkekUxumZg=",
                        "https://media.istockphoto.com/id/1324558913/es/foto/joven-confiado-en-camisa-verde-casual-mirando-hacia-otro-lado-de-pie-con-los-brazos-cruzados.jpg?s=612x612&w=0&k=20&c=Q09vBRkopP1XHvhMUxDYoHQgMoZvNQQEP-oLgjv6F2Q=",
                        "https://media.istockphoto.com/id/1919265357/es/foto/retrato-de-cerca-del-hombre-de-negocios-confiado-de-pie-en-la-oficina.jpg?s=612x612&w=0&k=20&c=b7fTAWYXKpfAyzO-gCSJpF3wYXWdhjdy4fZSEVj0bfI=",
                        "https://media.istockphoto.com/id/1399565382/es/foto/joven-empresario-mestizo-feliz-de-pie-con-los-brazos-cruzados-trabajando-solo-en-una-oficina.jpg?s=612x612&w=0&k=20&c=Tls7PDwhSbA9aaVg0RkpfPfWYaQrfecN319aOCKuU34=",
                        "https://media.istockphoto.com/id/1476170969/es/foto/retrato-de-un-joven-listo-para-el-trabajo-concepto-de-negocio.jpg?s=612x612&w=0&k=20&c=AAuUZOmmNYo6hzDYNR7d88Ihnxo4jrypqVJa-B8vjys=",
                        "https://media.istockphoto.com/id/1634444985/es/foto/feliz-due%C3%B1o-de-negocio-en-la-puerta-de-su-cafeter%C3%ADa-espacio-para-texto.jpg?s=612x612&w=0&k=20&c=HJsaJNNnPYW3kx2kI2Cs1vCPbx3lqAki3wBeJTjdiCI=",
                        "https://media.istockphoto.com/id/1439906987/es/foto/joven-pasante-exitoso-en-anteojos-y-camisa-marr%C3%B3n.jpg?s=612x612&w=0&k=20&c=O44mj6L0tIClTtzrtRySa_ee0ofrONhPhuMBbWVswXw=",
                        "https://media.istockphoto.com/id/1603245528/es/foto/apuesto-hombre-de-negocios-hispano-con-los-brazos-cruzados-sonriendo-a-la-c%C3%A1mara-indio-o.jpg?s=612x612&w=0&k=20&c=VwI4xGkN5h0ZbbiXg5RRN91M1W6zdHTYRhgC-Dpc4AU=",
                        "https://media.istockphoto.com/id/1616727526/es/foto/feliz-joven-hombre-de-negocios-latino-sosteniendo-una-tableta-de-pie-en-la-oficina-retrato.jpg?s=612x612&w=0&k=20&c=mVjlURtkBLuoUWGZFY-8IhDV46npIRUh6JrKFjFw-TI=",
                        "https://media.istockphoto.com/id/1059661424/es/foto/hombre-de-negocios-maduro-de-raza-mixta.jpg?s=612x612&w=0&k=20&c=z0tE_G3MMfZ6kqgacSfBzhqR5oGHZGcquds7GrOhlY4=",
                        "https://media.istockphoto.com/id/1491701395/es/foto/un-hombre-de-negocios-rubio-guapo-y-feliz-con-gafas-mirando-a-la-c%C3%A1mara.jpg?s=612x612&w=0&k=20&c=ifnBoUYI3731HQIgbjhetrLd7BXiGXuEg6uRBfTLNT4=",
                        "https://media.istockphoto.com/id/1352495212/es/foto/retrato-de-un-joven-y-amable-hombre-de-negocios.jpg?s=612x612&w=0&k=20&c=lvb_5AbA3K0hcBrf8WnDuQyswrChqVh1Fh9ZyJw5Aow=",
                        "https://media.istockphoto.com/id/1911521695/es/foto/successful-businessman-in-modern-office-working-on-laptop.jpg?s=612x612&w=0&k=20&c=n6KLrQMD8WWiJJ6W1oJf7ufWeIMCO6W2LAGy1nApo38=",
                        "https://media.istockphoto.com/id/1492359326/es/foto/retrato-tableta-digital-y-hombre-negro-en-la-oficina-feliz-sonriente-y-empoderado-ambici%C3%B3n-y.jpg?s=612x612&w=0&k=20&c=qr4m8aYYdpt-LLXYvw2a2svAcwQUxiV0poUxTYlQvAU=",
                        "https://media.istockphoto.com/id/1598828828/es/foto/retrato-de-un-exitoso-jefe-maduro-un-hombre-de-negocios-de-alto-rango-en-traje-de-negocios.jpg?s=612x612&w=0&k=20&c=O_Li8ilA392xa_eUs89h4O0_UKIowoL2ZL8HZ8N6unM=",
                        "https://media.istockphoto.com/id/1522377399/es/foto/retrato-exitoso-gerente-de-negocios-masculino-afroamericano-y-asesor-financiero.jpg?s=612x612&w=0&k=20&c=KMS3fRRHcO1wHvERucmuIPisAkrlnEHej4Hp683ajWk=",
                        "https://media.istockphoto.com/id/1495337064/es/foto/retrato-del-ceo-sonriente-en-un-lugar-de-trabajo-de-oficina-moderno-en-traje.jpg?s=612x612&w=0&k=20&c=IQr8imMe_-s8cS37FcA2LSZG24cJHhbvam63eS1UiUg=",
                        "https://media.istockphoto.com/id/1413766112/es/foto/exitoso-hombre-de-negocios-maduro-mirando-a-la-c%C3%A1mara-con-confianza.jpg?s=612x612&w=0&k=20&c=_wh29d41PN8a3GlqANKphBMIkN2P-QI4KPPIM7bVvDA=",
                        "https://media.istockphoto.com/id/1354898581/es/foto/foto-de-un-joven-empresario-usando-una-computadora-port%C3%A1til-en-una-oficina-moderna.jpg?s=612x612&w=0&k=20&c=ill7Gebgk_9_xh-pai6iODyhz1x644E_WhwzZgVbS6I=",
                        "https://media.istockphoto.com/id/1636023306/es/foto/retrato-de-un-joven-empresario-hispano-dentro-de-la-oficina-jefe-en-traje-de-negocios.jpg?s=612x612&w=0&k=20&c=gPJXKniQF2mHaPjC55--A2dNuhYaKRTonmUsXP5WLx4=",
                        "https://media.istockphoto.com/id/1370020940/es/foto/retrato-de-empresario-de-%C3%A9xito.jpg?s=612x612&w=0&k=20&c=r2-oFzMTgIo1CYKN_oSu-CJZztfSGKj6ylIt--nwXRA=",
                        "https://media.istockphoto.com/id/1435745704/es/foto/retrato-de-un-sonriente-hombre-de-negocios-adulto-medio-parado-en-la-oficina-corporativa.jpg?s=612x612&w=0&k=20&c=7QppbLJjVPWCOw0PupJc_qNMvTp7Pwrq7ue_FhuS5gU=",
                        "https://media.istockphoto.com/id/1488389611/es/foto/sonrisa-liderazgo-y-retrato-de-hombre-negro-ceo-seguro-de-s%C3%AD-mismo-con-maqueta-y-fondo-borroso.jpg?s=612x612&w=0&k=20&c=cYE33T94b_8IB04UAlSWZqSZ581iWw6I2NkljTutgdg="
        };
        private static final String[] ESPECIALIDADES_VETERINARIOS = {
                        "Cirugía",
                        "Dermatología",
                        "Cardiología",
                        "Oncología",
                        "Neurología",
                        "Oftalmología",
                        "Ortopedia",
                        "Rehabilitación",
                        "Medicina interna",
                        "Odontología veterinaria",
                        "Etología",
                        "Anestesiología",
                        "Gastroenterología",
                        "Urología",
                        "Infectología"
        };

        private static final String[] NOMBRES_VETERINARIOS = {
                        "Dr. Juan Pérez",
                        "Dra. Laura Martínez",
                        "Dr. Carlos Gómez",
                        "Dra. Sofía Ramírez",
                        "Dr. Andrés Torres",
                        "Dra. Elena Morales",
                        "Dr. Ricardo Sánchez",
                        "Dra. Ana Rodríguez",
                        "Dr. Luis Herrera",
                        "Dra. Valentina Cruz"
        };

        private static final String[] IMAGENES_VETERINARIOS_HOMBRES = {
                        "https://media.istockphoto.com/id/2025170211/es/foto/cheerful-mature-doctor-posing-and-smiling-at-camera-healthcare-and-medicine.jpg?s=612x612&w=0&k=20&c=-fcBpqjAZt7kfIDAoU6EdxSqUCBKY0sqIao4ICckCjg=",
                        "https://media.istockphoto.com/id/2158610739/es/foto/m%C3%A9dico-guapo-con-estetoscopio-sobre-el-cuello-trabajando-mientras-mira-a-la-c%C3%A1mara-en-la.jpg?s=612x612&w=0&k=20&c=meqU3H6FI07LqfM8Iyr8FSTwpMAjcOs6U-bQbilV8HE=",
                        "https://media.istockphoto.com/id/1346124900/es/foto/m%C3%A9dico-maduro-exitoso-y-seguro-en-el-hospital.jpg?s=612x612&w=0&k=20&c=b78Z4EqidP32vGxSYh4xzacwUZaGdJtj8RLNQwM8ESc=",
                        "https://media.istockphoto.com/id/1189304032/es/foto/m%C3%A9dico-sosteniendo-tableta-digital-en-la-sala-de-reuniones.jpg?s=612x612&w=0&k=20&c=pmijXzja8qGwKXlqt7YWzSUkxFxnODfK6u7B1QXd1wU=",
                        "https://media.istockphoto.com/id/1161336374/es/foto/retrato-de-joven-m%C3%A9dico-confiado-sobre-fondo-azul.jpg?s=612x612&w=0&k=20&c=fkXKNaF8fmeibTO8BmKEn4Ntv7vn-vtZRUGph4DwLXc="
        };

        private static final String[] IMAGENES_VETERINARIOS_MUJERES = {
                        "https://media.istockphoto.com/id/1806608544/es/foto/retrato-de-una-doctora-en-el-lugar-de-trabajo.jpg?s=612x612&w=0&k=20&c=6HapTwe196GOqZT0a9b1etZWVM7cz-XiAjaryDNW3oA=",
                        "https://media.istockphoto.com/id/2050883733/es/foto/feliz-trabajadora-m%C3%A9dica-exitosa-de-la-doctora-en-bata-blanca-de-laboratorio-de-pie-con-los.jpg?s=612x612&w=0&k=20&c=RsztfjZX_AS5qqij4v-Tm0qMfVUpJc9XJRIuoLxk3ng=",
                        "https://media.istockphoto.com/id/2153805399/es/foto/retrato-de-una-doctora-feliz-de-pie-afuera-frente-a-un-hospital-moderno.jpg?s=612x612&w=0&k=20&c=oYjJu3Z-qi-T-t4CwC83O2-GLUZ0_EXpVSCATU1JuJw=",
                        "https://media.istockphoto.com/id/1633320190/es/foto/mujer-feliz-o-cara-de-m%C3%A9dico-en-un-hospital-ocupado-con-tableta-para-servicios-de-atenci%C3%B3n.jpg?s=612x612&w=0&k=20&c=_vpjC50IXHCcfpPFg_DPcdYSKUmcBxHsGNUz23hcPSU=",
                        "https://media.istockphoto.com/id/1352251596/es/foto/m%C3%A9dico-al-frente-de-un-grupo-de-trabajadores-sanitarios-del-hospital.jpg?s=612x612&w=0&k=20&c=GzyiH2FqAJzzoLmxcPc90eYpHH0i6o68ggkKqgFTA8g="

        };

        private String generarCorreo(String nombre, String apellido, int i) {
                return nombre.toLowerCase().replace(" ", "") + "." + apellido.toLowerCase().replace(" ", "") + i
                                + "@example.com";
        }

        private String generarPasswordDeterminista(String nombre, int indice) {
                Random random = new Random((nombre + indice).hashCode());
                return random.ints(48, 123)
                                .filter(j -> (j <= 57 || j >= 65) && (j <= 90 || j >= 97))
                                .limit(8)
                                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                                .toString();
        }

        @Override
        public void run(ApplicationArguments args) throws Exception {
                if (!GENERAR_DATOS) {
                        System.out.println("Generación de datos desactivada.");
                        return;
                }

                System.out.println("Borrando datos existentes...");
                tratamientoRepositorio.deleteAll();
                mascotaRepositorio.deleteAll();
                duenoRepositorio.deleteAll();
                veterinarioRepositorio.deleteAll();
                medicamentoRepositorio.deleteAll();

                System.out.println("Generando datos de prueba...");
                Random random = new Random(0);

                Dueno[] duenos = new Dueno[50];
                for (int i = 0; i < 50; i++) {
                        boolean esHombre = random.nextBoolean();
                        String nombre = esHombre ? NOMBRES_HOMBRES[random.nextInt(NOMBRES_HOMBRES.length)]
                                        : NOMBRES_MUJERES[random.nextInt(NOMBRES_MUJERES.length)];
                        String apellido = APELLIDOS[random.nextInt(APELLIDOS.length)];
                        String email = generarCorreo(nombre, apellido, i);
                        String imagenUrl = esHombre
                                        ? IMAGENES_DUENOS_HOMBRES[random.nextInt(IMAGENES_DUENOS_HOMBRES.length)]
                                        : IMAGENES_DUENOS_MUJERES[random.nextInt(IMAGENES_DUENOS_MUJERES.length)];

                        Dueno dueno = Dueno.builder()
                                        .cedula(1000000L + i)
                                        .nombre(nombre + " " + apellido)
                                        .email(email)
                                        .telefono("300000000" + i)
                                        .imagenUrl(imagenUrl)
                                        .password(generarPasswordDeterminista(nombre, i))
                                        .build();
                        duenos[i] = duenoRepositorio.save(dueno);

                }

                List<Veterinario> vets = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                        String estado = (i % 2 == 0) ? "Activo" : "Inactivo";
                        String nombre = NOMBRES_VETERINARIOS[i];

                        // Verificar género por el título del nombre
                        boolean esMujer = nombre.startsWith("Dra.");
                        String imagenUrl = esMujer
                                        ? IMAGENES_VETERINARIOS_MUJERES[random
                                                        .nextInt(IMAGENES_VETERINARIOS_MUJERES.length)]
                                        : IMAGENES_VETERINARIOS_HOMBRES[random
                                                        .nextInt(IMAGENES_VETERINARIOS_HOMBRES.length)];

                        Veterinario v = new Veterinario(
                                        100100100L + i,
                                        nombre,
                                        generarPasswordDeterminista(estado, i),
                                        ESPECIALIDADES_VETERINARIOS[random.nextInt(ESPECIALIDADES_VETERINARIOS.length)],
                                        imagenUrl,
                                        100 + i,
                                        estado);
                        vets.add(v);
                }
                veterinarioRepositorio.saveAll(vets);

                for (int i = 0; i < 100; i++) {
                        String especie = ESPECIES[random.nextInt(ESPECIES.length)];
                        String imagenMascota = especie.equals("Perro")
                                        ? IMAGENES_PERROS[random.nextInt(IMAGENES_PERROS.length)]
                                        : IMAGENES_GATOS[random.nextInt(IMAGENES_GATOS.length)];
                        String estado = (i % 2 == 0) ? "Activo" : "Inactivo";

                        Mascota mascota = new Mascota(
                                        NOMBRES_MASCOTAS[random.nextInt(NOMBRES_MASCOTAS.length)],
                                        especie,
                                        random.nextInt(15) + 1,
                                        5.0 + random.nextDouble() * 10,
                                        ENFERMEDADES[random.nextInt(ENFERMEDADES.length)],
                                        imagenMascota,
                                        estado);
                        mascota.setDueno(duenos[random.nextInt(duenos.length)]);
                        mascotaRepositorio.save(mascota);
                }

                List<Medicamento> meds = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                        Medicamento m = new Medicamento("Med" + i, 5.0 + i, 7.0 + i, 50 + i, 10 + i);
                        meds.add(m);
                }
                medicamentoRepositorio.saveAll(meds);

                List<Mascota> mascotas = mascotaRepositorio.findAll();
                List<Tratamiento> tratamientos = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                        Tratamiento t = new Tratamiento(java.time.LocalDate.now().minusDays(i), "Tratamiento " + i,
                                        vets.get(i),
                                        mascotas.get(i), meds.get(i), i + 1);
                        tratamientos.add(t);
                }
                tratamientoRepositorio.saveAll(tratamientos);

                // Asignar veterinarios a las mascotas
                System.out.println("Asignando veterinarios a las mascotas...");
                List<Mascota> todasLasMascotas = mascotaRepositorio.findAll();
                List<Veterinario> todosLosVeterinarios = veterinarioRepositorio.findAll();

                // Crear un mapa de mascotas y sus veterinarios basado en tratamientos
                java.util.Map<Long, Veterinario> mascotaVeterinarioMap = new java.util.HashMap<>();
                List<Tratamiento> todosTratamientos = tratamientoRepositorio.findAll();

                for (Tratamiento tratamiento : todosTratamientos) {
                        if (tratamiento.getMascota() != null && tratamiento.getVeterinario() != null) {
                                mascotaVeterinarioMap.put(tratamiento.getMascota().getMascotaId(),
                                                tratamiento.getVeterinario());
                        }
                }

                Random r = new Random();
                for (Mascota mascota : todasLasMascotas) {
                        Veterinario veterinarioAsignado;

                        // Si la mascota tiene un tratamiento, usar ese veterinario
                        if (mascotaVeterinarioMap.containsKey(mascota.getMascotaId())) {
                                veterinarioAsignado = mascotaVeterinarioMap.get(mascota.getMascotaId());
                        } else {
                                // Asignar un veterinario aleatorio
                                veterinarioAsignado = todosLosVeterinarios.get(r.nextInt(todosLosVeterinarios.size()));
                        }

                        // Establecer el veterinario en la mascota
                        mascota.setVeterinario(veterinarioAsignado);
                }

                // Guardar las mascotas actualizadas
                mascotaRepositorio.saveAll(todasLasMascotas);

                System.out.println("Datos generados exitosamente.");
        }
}
