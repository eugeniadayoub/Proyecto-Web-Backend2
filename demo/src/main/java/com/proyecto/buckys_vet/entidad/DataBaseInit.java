package com.proyecto.buckys_vet.entidad;

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

import com.proyecto.buckys_vet.repositorio.DuenoRepositorio;
import com.proyecto.buckys_vet.repositorio.MascotaRepositorio;
import com.proyecto.buckys_vet.repositorio.MedicamentoRepositorio;
import com.proyecto.buckys_vet.repositorio.TratamientoRepositorio;
import com.proyecto.buckys_vet.repositorio.VeterinarioRepositorio;

import jakarta.transaction.Transactional;

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

    private static final String[] NOMBRES_MUJERES = {
        "María", "Ana", "Luisa", "Marta", "Elena", "Lucía", "Gabriela", "Valentina", "Diana", "Rosa", 
        "Daniela", "Mariana", "Carla"
    };

    private static final String[] NOMBRES_HOMBRES = {
        "Carlos", "Luis", "Jorge", "Roberto", "Pablo", "Andrés", "Francisco", "Esteban", "Fernando", "Ricardo", 
        "Emilio", "Alejandro", "Javier", "Tomás"
    };

    private static final String[] APELLIDOS = {
        "Gómez", "Pérez", "Fernández", "López", "Martínez", "Gutiérrez", "Rodríguez", "Vargas", "Castro", "Torres", 
        "Ramírez", "Muñoz", "Ortega", "Moreno", "Salazar", "Núñez", "Mendoza", "Herrera", "Cabrera", "Santana",
        "Chávez", "Reyes", "Benítez", "Esquivel", "Navarro", "Fuentes", "Ávila", "Mejía", "Soto", "Acosta"
    };

    private static final String[] NOMBRES_MASCOTAS = {
        "Luna", "Simba", "Coco", "Rocky", "Bella", "Max", "Milo", "Toby", "Rex", "Nala", 
        "Kira", "Loki", "Bruno", "Daisy", "Chester", "Sasha", "Thor", "Boby", "Zeus", "Canela", 
        "Maya", "Apolo", "Tina", "Bobby", "Fiona", "Terry", "Romeo", "Mimi", "Odin", "Leia"
    };

    private static final String[] ESPECIES = {"Perro", "Gato"};

    private static final String[] ENFERMEDADES = {
        "Ninguna", "Alergia alimentaria", "Parvovirus", "Displasia de cadera", "Artrosis", "Infección en el oído",
        "Moquillo canino", "Insuficiencia renal", "Hipotiroidismo", "Leishmaniasis", "Gastroenteritis", "Epilepsia",
        "Otitis", "Diabetes", "Anemia", "Tumor mamario", "Pancreatitis", "Obesidad", "Leucemia felina", "Rabia"
    };

    private static final String[] IMAGENES_PERROS = {
        "https://cdn.pixabay.com/photo/2023/08/18/15/02/dog-8198719_1280.jpg", "https://cdn.pixabay.com/photo/2020/11/22/20/12/schafer-dog-5767834_1280.jpg", 
        "https://cdn.pixabay.com/photo/2015/11/17/13/13/bulldog-1047518_1280.jpg", "https://cdn.pixabay.com/photo/2016/11/29/11/26/dog-1869167_1280.jpg", 
        "https://cdn.pixabay.com/photo/2017/03/27/13/23/dog-2178696_1280.jpg", "https://cdn.pixabay.com/photo/2016/12/13/05/15/puppy-1903313_1280.jpg",
        "https://cdn.pixabay.com/photo/2016/07/15/15/55/dachshund-1519374_1280.jpg", "https://cdn.pixabay.com/photo/2016/10/26/22/02/dog-1772759_1280.jpg",
        "https://cdn.pixabay.com/photo/2016/02/11/17/00/dog-1194087_1280.jpg", "https://cdn.pixabay.com/photo/2016/11/23/18/06/dog-1854119_1280.jpg", 
        "https://cdn.pixabay.com/photo/2018/05/07/10/48/husky-3380548_1280.jpg", "https://cdn.pixabay.com/photo/2018/03/31/06/31/dog-3277414_1280.jpg",
        "https://cdn.pixabay.com/photo/2016/10/15/12/01/dog-1742295_1280.jpg", "https://cdn.pixabay.com/photo/2014/03/14/20/13/dog-287420_1280.jpg",
        "https://cdn.pixabay.com/photo/2017/07/31/21/15/dog-2561134_1280.jpg", "https://cdn.pixabay.com/photo/2014/12/10/05/50/english-bulldog-562723_1280.jpg",
        "https://cdn.pixabay.com/photo/2018/03/18/18/06/australian-shepherd-3237735_1280.jpg", "https://cdn.pixabay.com/photo/2017/02/01/09/48/jack-russell-2029214_1280.jpg",
        "https://cdn.pixabay.com/photo/2021/07/17/23/01/dog-6474269_1280.jpg", "https://cdn.pixabay.com/photo/2019/05/27/19/08/puppy-4233378_1280.jpg",
        "https://cdn.pixabay.com/photo/2021/01/21/16/17/english-cocker-spaniel-5937757_1280.jpg", "https://cdn.pixabay.com/photo/2019/06/05/08/37/dog-4253238_1280.jpg",
        "https://cdn.pixabay.com/photo/2021/04/07/05/39/labrador-retriever-6158095_1280.jpg", "https://cdn.pixabay.com/photo/2021/08/18/22/42/australian-shepherd-6556697_1280.jpg",
        "https://cdn.pixabay.com/photo/2022/07/09/17/42/dog-7311407_1280.jpg", "https://cdn.pixabay.com/photo/2015/06/24/13/32/dog-820014_1280.jpg",
        "https://cdn.pixabay.com/photo/2020/11/08/10/25/dog-5723334_1280.jpg", "https://cdn.pixabay.com/photo/2021/07/05/14/07/dog-6389277_1280.jpg",
        "https://cdn.pixabay.com/photo/2017/06/24/09/13/continental-bulldog-2437110_1280.jpg", "https://cdn.pixabay.com/photo/2018/05/09/16/15/dog-3385541_1280.jpg",
        "https://cdn.pixabay.com/photo/2020/05/03/13/09/puppy-5124947_1280.jpg", "https://cdn.pixabay.com/photo/2015/05/24/22/33/german-longhaired-pointer-782498_1280.jpg",
        "https://cdn.pixabay.com/photo/2016/11/29/09/58/dog-1868871_1280.jpg", "https://cdn.pixabay.com/photo/2022/01/17/19/59/dog-6945696_1280.jpg",
        "https://cdn.pixabay.com/photo/2021/08/21/19/39/greyhound-6563435_1280.jpg", "https://cdn.pixabay.com/photo/2022/03/30/11/12/dog-7101015_1280.jpg",
        "https://cdn.pixabay.com/photo/2015/05/13/08/07/dalmatians-765138_1280.jpg", "https://cdn.pixabay.com/photo/2023/03/18/17/48/basset-hound-7861037_1280.jpg",
        "https://cdn.pixabay.com/photo/2016/11/29/01/24/dog-1866530_1280.jpg", "https://cdn.pixabay.com/photo/2023/04/28/14/35/dog-7956828_1280.jpg",
        "https://cdn.pixabay.com/photo/2023/10/01/12/27/border-collie-8287329_1280.jpg", "https://cdn.pixabay.com/photo/2019/08/07/14/10/golden-retriever-4390884_1280.jpg",
        "https://cdn.pixabay.com/photo/2022/02/06/14/06/dog-6997211_1280.jpg", "https://cdn.pixabay.com/photo/2020/11/20/16/26/labrador-5762115_1280.jpg",
        "https://cdn.pixabay.com/photo/2017/05/09/23/02/dog-2299482_1280.jpg", "https://cdn.pixabay.com/photo/2017/09/25/13/11/dog-2785066_1280.jpg",
        "https://cdn.pixabay.com/photo/2023/03/02/14/46/pit-bull-7825554_1280.jpg", "https://cdn.pixabay.com/photo/2024/02/05/16/23/labrador-8554882_1280.jpg",
        "https://cdn.pixabay.com/photo/2023/04/15/17/08/bernese-mountain-dog-7928156_1280.jpg", "https://cdn.pixabay.com/photo/2022/04/12/19/35/dog-7128749_1280.jpg"
    };

    private static final String[] IMAGENES_GATOS = {
        "https://cdn.pixabay.com/photo/2023/08/18/01/32/cat-8197577_1280.jpg", "https://cdn.pixabay.com/photo/2022/06/19/04/25/cat-7271017_1280.jpg",
        "https://cdn.pixabay.com/photo/2016/01/20/13/05/cat-1151519_1280.jpg", "https://cdn.pixabay.com/photo/2014/11/30/14/11/cat-551554_1280.jpg",
        "https://cdn.pixabay.com/photo/2018/10/11/12/31/cat-3739702_1280.jpg", "https://cdn.pixabay.com/photo/2023/09/21/17/05/european-shorthair-8267220_1280.jpg",
        "https://cdn.pixabay.com/photo/2014/10/01/10/46/cat-468232_1280.jpg", "https://cdn.pixabay.com/photo/2022/07/03/22/00/cat-7300029_1280.jpg",
        "https://cdn.pixabay.com/photo/2020/08/17/18/38/cat-5496162_1280.jpg", "https://cdn.pixabay.com/photo/2012/10/12/17/12/cat-61079_1280.jpg",
        "https://cdn.pixabay.com/photo/2020/05/04/14/14/cat-5129332_1280.jpg", "https://cdn.pixabay.com/photo/2014/03/29/09/17/cat-300572_1280.jpg",
        "https://cdn.pixabay.com/photo/2017/07/22/15/21/cat-2528935_1280.jpg", "https://cdn.pixabay.com/photo/2024/03/07/10/38/simba-8618301_1280.jpg",
        "https://cdn.pixabay.com/photo/2018/05/14/21/43/british-shorthair-3401683_1280.jpg", "https://cdn.pixabay.com/photo/2013/05/17/15/54/cat-111793_1280.jpg",
        "https://cdn.pixabay.com/photo/2017/03/14/14/49/cat-2143332_1280.jpg", "https://cdn.pixabay.com/photo/2018/04/20/17/18/cat-3336579_1280.jpg",
        "https://cdn.pixabay.com/photo/2020/09/17/13/59/cat-5579221_1280.jpg", "https://cdn.pixabay.com/photo/2020/11/26/11/48/cat-5778777_1280.jpg",
        "https://cdn.pixabay.com/photo/2021/06/04/14/14/cat-6309964_1280.jpg", "https://cdn.pixabay.com/photo/2022/03/27/11/23/cat-7094808_1280.jpg",
        "https://cdn.pixabay.com/photo/2018/07/08/14/16/cat-3523992_1280.jpg", "https://cdn.pixabay.com/photo/2014/06/03/01/31/cat-360807_1280.jpg",
        "https://cdn.pixabay.com/photo/2019/06/12/15/07/cat-4269479_1280.jpg", "https://cdn.pixabay.com/photo/2018/08/08/05/12/cat-3591348_1280.jpg",
        "https://cdn.pixabay.com/photo/2021/11/02/10/46/cat-6762936_1280.jpg", "https://cdn.pixabay.com/photo/2018/06/28/14/12/cat-3504008_1280.jpg",
        "https://cdn.pixabay.com/photo/2016/11/18/21/57/animal-1837067_1280.jpg", "https://cdn.pixabay.com/photo/2019/06/08/17/02/cat-4260536_1280.jpg",
        "https://cdn.pixabay.com/photo/2017/08/07/12/27/cat-2603300_1280.jpg", "https://cdn.pixabay.com/photo/2021/12/01/18/17/cat-6838844_1280.jpg",
        "https://cdn.pixabay.com/photo/2015/11/16/22/14/cat-1046544_1280.jpg", "https://cdn.pixabay.com/photo/2020/11/25/03/04/russian-blue-cat-5774414_1280.jpg",
        "https://cdn.pixabay.com/photo/2020/11/22/17/28/cat-5767334_1280.jpg", "https://cdn.pixabay.com/photo/2019/12/17/16/06/cat-4701934_1280.jpg",
        "https://cdn.pixabay.com/photo/2012/11/26/13/58/cat-67345_1280.jpg", "https://cdn.pixabay.com/photo/2015/01/31/12/36/cat-618470_1280.jpg",
        "https://cdn.pixabay.com/photo/2018/01/14/14/42/cat-3081939_1280.jpg", "https://cdn.pixabay.com/photo/2021/07/13/11/34/cat-6463284_1280.jpg",
        "https://cdn.pixabay.com/photo/2023/06/29/12/28/cats-8096304_1280.jpg", "https://cdn.pixabay.com/photo/2016/11/19/21/09/cat-1841202_1280.jpg",
        "https://cdn.pixabay.com/photo/2023/04/07/07/14/cat-7905702_1280.jpg", "https://cdn.pixabay.com/photo/2023/12/08/05/38/cat-8436843_1280.jpg",
        "https://cdn.pixabay.com/photo/2023/05/19/19/43/cat-8005275_1280.jpg", "https://cdn.pixabay.com/photo/2021/07/15/10/47/cat-6468112_1280.jpg",
        "https://cdn.pixabay.com/photo/2021/10/27/19/09/cat-6748193_1280.jpg", "https://cdn.pixabay.com/photo/2015/11/15/20/21/cat-1044750_1280.jpg",
        "https://cdn.pixabay.com/photo/2023/03/09/20/02/cat-7840767_1280.jpg", "https://cdn.pixabay.com/photo/2024/02/28/07/42/european-shorthair-8601492_1280.jpg"
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
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("\n=== INICIALIZANDO BASE DE DATOS ===");
        Random random = new Random();
        random.setSeed(0);

        // Actualizar contraseñas de dueños existentes
        actualizarContrasenasDuenos(random);

        // Crear 50 dueños con nombre y apellido aleatorio
        Dueno[] duenos = new Dueno[50];
        for (int i = 0; i < 50; i++) {
            // Elegimos un nombre al azar
            boolean esHombre = random.nextBoolean();
            String nombre = esHombre 
                ? NOMBRES_HOMBRES[random.nextInt(NOMBRES_HOMBRES.length)] 
                : NOMBRES_MUJERES[random.nextInt(NOMBRES_MUJERES.length)];
            
            String apellido = APELLIDOS[random.nextInt(APELLIDOS.length)];
            String email = generarCorreo(nombre, apellido);

            // Seleccionamos la imagen de acuerdo al género
            String imagenUrl = esHombre 
                    ? IMAGENES_DUENOS_HOMBRES[random.nextInt(IMAGENES_DUENOS_HOMBRES.length)] 
                    : IMAGENES_DUENOS_MUJERES[random.nextInt(IMAGENES_DUENOS_MUJERES.length)];

            Dueno dueno = new Dueno();
            dueno.setCedula(1000000L + random.nextInt(9000000));
            dueno.setNombre(nombre + " " + apellido);
            dueno.setEmail(email);
            dueno.setTelefono("300" + String.format("%07d", random.nextInt(10000000)));
            dueno.setImagenUrl(imagenUrl);
            dueno.setPassword(random.ints(48, 123)
                .filter(j -> (j <= 57 || j >= 65) && (j <= 90 || j >= 97))
                .limit(8)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString());

            duenos[i] = duenoRepositorio.save(dueno);
            System.out.println("Dueño creado - ID: " + duenos[i].getIdDueno() + ", Nombre: " + duenos[i].getNombre() 
                + ", Imagen: " + imagenUrl + ", Password: " + duenos[i].getPassword());
        }

        // Asignar al menos una mascota a cada dueño
        for (int i = 0; i < 50; i++) {
            String especie = ESPECIES[random.nextInt(ESPECIES.length)];
            String imagenUrl = especie.equals("Perro") 
                ? IMAGENES_PERROS[random.nextInt(IMAGENES_PERROS.length)] 
                : IMAGENES_GATOS[random.nextInt(IMAGENES_GATOS.length)];
            Mascota mascota = new Mascota(
                NOMBRES_MASCOTAS[random.nextInt(NOMBRES_MASCOTAS.length)],
                especie,
                random.nextInt(15) + 1, // Edad entre 1 y 15 años
                Math.round((random.nextDouble() * 30 + 1) * 100.0) / 100.0, // Peso redondeado a 2 decimales
                ENFERMEDADES[random.nextInt(ENFERMEDADES.length)],
                imagenUrl,
                random.nextBoolean() ? "Activo" : "Inactivo"
            );
            mascota.setDueno(duenos[i]);
            mascotaRepositorio.save(mascota);
            System.out.println("Mascota " + mascota.getNombre() + " creada para " + mascota.getDueno().getNombre() + " - ID Mascota: " + mascota.getMascotaId());
        }

        // Crear 50 mascotas adicionales y asignarlas aleatoriamente
        for (int i = 0; i < 50; i++) {
            String especie = ESPECIES[random.nextInt(ESPECIES.length)];
            String imagenUrl = especie.equals("Perro") 
                ? IMAGENES_PERROS[random.nextInt(IMAGENES_PERROS.length)] 
                : IMAGENES_GATOS[random.nextInt(IMAGENES_GATOS.length)];

            Mascota nuevaMascota = new Mascota(
                NOMBRES_MASCOTAS[random.nextInt(NOMBRES_MASCOTAS.length)],
                especie,
                random.nextInt(15) + 1,
                Math.round((random.nextDouble() * 30 + 1) * 100.0) / 100.0,
                ENFERMEDADES[random.nextInt(ENFERMEDADES.length)],
                imagenUrl,
                random.nextBoolean() ? "Activo" : "Inactivo"
            );

            nuevaMascota.setDueno(duenos[random.nextInt(duenos.length)]);
            mascotaRepositorio.save(nuevaMascota);
            System.out.println("Mascota " + nuevaMascota.getNombre() + " creada para " + nuevaMascota.getDueno().getNombre() + " - ID Mascota: " + nuevaMascota.getMascotaId());
        }
         Veterinario v1 = new Veterinario(100100100L, "Dr. Juan Pérez", "claveJuan123", "Cirugía", "https://media.istockphoto.com/id/2025170211/es/foto/cheerful-mature-doctor-posing-and-smiling-at-camera-healthcare-and-medicine.jpg?s=612x612&w=0&k=20&c=-fcBpqjAZt7kfIDAoU6EdxSqUCBKY0sqIao4ICckCjg=", 150);
        Veterinario v2 = new Veterinario(100100101L, "Dra. María Gómez", "claveMaria123", "Oncología", "https://media.istockphoto.com/id/1806608544/es/foto/retrato-de-una-doctora-en-el-lugar-de-trabajo.jpg?s=612x612&w=0&k=20&c=6HapTwe196GOqZT0a9b1etZWVM7cz-XiAjaryDNW3oA=", 180);
        Veterinario v3 = new Veterinario(100100102L, "Dr. Carlos López", "claveCarlos123", "Fisioterapia", "https://media.istockphoto.com/id/2158610739/es/foto/m%C3%A9dico-guapo-con-estetoscopio-sobre-el-cuello-trabajando-mientras-mira-a-la-c%C3%A1mara-en-la.jpg?s=612x612&w=0&k=20&c=meqU3H6FI07LqfM8Iyr8FSTwpMAjcOs6U-bQbilV8HE=", 95);
        Veterinario v4 = new Veterinario(100100103L, "Dra. Ana Rodríguez", "claveAna123", "Rehabilitación", "https://media.istockphoto.com/id/1633320190/es/foto/mujer-feliz-o-cara-de-m%C3%A9dico-en-un-hospital-ocupado-con-tableta-para-servicios-de-atenci%C3%B3n.jpg?s=612x612&w=0&k=20&c=_vpjC50IXHCcfpPFg_DPcdYSKUmcBxHsGNUz23hcPSU=", 200);
        Veterinario v5 = new Veterinario(100100104L, "Dr. Luis Martínez", "claveLuis123", "Ortopedia", "https://media.istockphoto.com/id/1346124900/es/foto/m%C3%A9dico-maduro-exitoso-y-seguro-en-el-hospital.jpg?s=612x612&w=0&k=20&c=b78Z4EqidP32vGxSYh4xzacwUZaGdJtj8RLNQwM8ESc=", 110);
        Veterinario v6 = new Veterinario(100100105L, "Dra. Elena Morales", "claveElena123", "Oftalmología", "https://media.istockphoto.com/id/1189304032/es/foto/m%C3%A9dico-sosteniendo-tableta-digital-en-la-sala-de-reuniones.jpg?s=612x612&w=0&k=20&c=pmijXzja8qGwKXlqt7YWzSUkxFxnODfK6u7B1QXd1wU=", 130);
        Veterinario v7 = new Veterinario(100100106L, "Dr. Ricardo Sánchez", "claveRicardo123", "Cardiología", "https://media.istockphoto.com/id/1161336374/es/foto/retrato-de-joven-m%C3%A9dico-confiado-sobre-fondo-azul.jpg?s=612x612&w=0&k=20&c=fkXKNaF8fmeibTO8BmKEn4Ntv7vn-vtZRUGph4DwLXc=", 170);
        Veterinario v8 = new Veterinario(100100107L, "Dra. Sofía Ramírez", "claveSofia123", "Neurología", "https://media.istockphoto.com/id/2153805399/es/foto/retrato-de-una-doctora-feliz-de-pie-afuera-frente-a-un-hospital-moderno.jpg?s=612x612&w=0&k=20&c=oYjJu3Z-qi-T-t4CwC83O2-GLUZ0_EXpVSCATU1JuJw=", 160);
        Veterinario v9 = new Veterinario(100100108L, "Dr. Andrés Torres", "claveAndres123", "Gastroenterología", "https://media.istockphoto.com/id/1327024466/es/foto/retrato-de-un-m%C3%A9dico-var%C3%B3n-con-bata-blanca-y-estetoscopio-de-pie-en-la-sala-de-la-cl%C3%ADnica.jpg?s=612x612&w=0&k=20&c=JZB2CVdH8kwUVKMSdLS35LBdjcUKrfZsVQY3S46fsaQ=", 140);
        Veterinario v10 = new Veterinario(100100109L, "Dra. Laura Fernández", "claveLaura123", "Urología", "https://media.istockphoto.com/id/1425798958/es/foto/foto-de-una-doctora-confiada-en-el-hospital-mirando-a-la-c%C3%A1mara-con-una-sonrisa.jpg?s=612x612&w=0&k=20&c=JIu8o3ANyPehV0bJ9GO4pao2P6dejt6mXqEMHUzUYLk=", 125);
        
        veterinarioRepositorio.saveAll(Arrays.asList(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10));
        
        // Insertar 10 medicamentos
        Medicamento m1 = new Medicamento("Paracetamol", 5.0, 7.5, 100, 20);
        Medicamento m2 = new Medicamento("Ibuprofeno", 8.0, 12.0, 150, 40);
        Medicamento m3 = new Medicamento("Amoxicilina", 10.0, 15.0, 200, 50);
        Medicamento m4 = new Medicamento("Metronidazol", 9.0, 14.0, 80, 30);
        Medicamento m5 = new Medicamento("Antiparasitario", 12.0, 18.0, 120, 35);
        Medicamento m6 = new Medicamento("Antiviral", 15.0, 22.0, 90, 25);
        Medicamento m7 = new Medicamento("Antibiótico", 11.0, 16.0, 110, 30);
        Medicamento m8 = new Medicamento("Suplemento vitamínico", 4.0, 6.0, 250, 60);
        Medicamento m9 = new Medicamento("Jarabe para tos", 6.0, 9.0, 130, 40);
        Medicamento m10 = new Medicamento("Antiinflamatorio", 7.0, 10.0, 140, 45);
        
        medicamentoRepositorio.saveAll(Arrays.asList(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10));
        
        // Crear medicamentos adicionales para más variedad
        crearMedicamentosAdicionales(medicamentoRepositorio, random);
        
        // Insertar 10 tratamientos como tabla intermedia (usando algunas fechas de ejemplo)
        // Primero vamos a obtener las mascotas asociadas a los dueños para asegurarnos que existan
        List<Mascota> mascotasDuenos = new ArrayList<>();
        
        for (int i = 0; i < 10; i++) {
            List<Mascota> mascotasDueno = mascotaRepositorio.findByDuenoId(duenos[i].getIdDueno());
            if (!mascotasDueno.isEmpty()) {
                mascotasDuenos.add(mascotasDueno.get(0));
            } else {
                // Si no hay mascota para este dueño, crear una mascota genérica
                String especie = ESPECIES[random.nextInt(ESPECIES.length)];
                String imagenUrl = especie.equals("Perro") 
                    ? IMAGENES_PERROS[random.nextInt(IMAGENES_PERROS.length)] 
                    : IMAGENES_GATOS[random.nextInt(IMAGENES_GATOS.length)];
                    
                Mascota nuevaMascota = new Mascota(
                    NOMBRES_MASCOTAS[random.nextInt(NOMBRES_MASCOTAS.length)],
                    especie,
                    random.nextInt(15) + 1,
                    Math.round((random.nextDouble() * 30 + 1) * 100.0) / 100.0,
                    ENFERMEDADES[random.nextInt(ENFERMEDADES.length)],
                    imagenUrl,
                    random.nextBoolean() ? "Activo" : "Inactivo"
                );
                nuevaMascota.setDueno(duenos[i]);
                mascotaRepositorio.save(nuevaMascota);
                mascotasDuenos.add(nuevaMascota);
            }
        }
        
        // Ahora creamos los tratamientos con mascotas verificadas
        Tratamiento t1 = new Tratamiento(LocalDate.parse("2023-06-01"), "Aplicación de vacuna antirrábica", v1, 
        mascotasDuenos.get(0), m1, 2);
        Tratamiento t2 = new Tratamiento(LocalDate.parse("2023-06-05"), "Tratamiento para infección en piel", v2, 
                 mascotasDuenos.get(1), m2, 1);
        Tratamiento t3 = new Tratamiento(LocalDate.parse("2023-06-10"), "Cuidado post-operatorio de cirugía menor", v3, 
                mascotasDuenos.get(2), m3, 3);
        Tratamiento t4 = new Tratamiento(LocalDate.parse("2023-06-15"), "Terapia para alergias", v4, 
                 mascotasDuenos.get(3), m4, 1);
        Tratamiento t5 = new Tratamiento(LocalDate.parse("2023-06-20"), "Revisión general y actualización de vacunas", v5, 
                 mascotasDuenos.get(4), m5, 2);
        Tratamiento t6 = new Tratamiento(LocalDate.parse("2023-06-25"), "Tratamiento para desparasitación interna", v6, 
                 mascotasDuenos.get(5), m6, 1);
        Tratamiento t7 = new Tratamiento(LocalDate.parse("2023-07-01"), "Aplicación de antipulgas y garrapatas", v7, 
                mascotasDuenos.get(6), m7, 2);
        Tratamiento t8 = new Tratamiento(LocalDate.parse("2023-07-05"), "Rehabilitación post lesión", v8, 
                 mascotasDuenos.get(7), m8, 4);
        Tratamiento t9 = new Tratamiento(LocalDate.parse("2023-07-10"), "Control de infecciones respiratorias", v9, 
                 mascotasDuenos.get(8), m9, 3);
        Tratamiento t10 = new Tratamiento(LocalDate.parse("2023-07-15"), "Tratamiento para problemas digestivos", v10, 
                mascotasDuenos.get(9), m10, 2);
        
        tratamientoRepositorio.saveAll(Arrays.asList(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
        
        // Crear tratamientos adicionales
        //crearTratamientosAdicionales(duenos, mascotaRepositorio, veterinarioRepositorio, medicamentoRepositorio, tratamientoRepositorio, random);

        System.out.println("\n=== INICIALIZACIÓN COMPLETADA ===");
    }

    private String generarCorreo(String nombre, String apellido) {
        return normalizarTexto(nombre) + "." + normalizarTexto(apellido) + "@email.com".toLowerCase();
    }

    private String normalizarTexto(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    private void actualizarContrasenasDuenos(Random random) {
        List<Dueno> duenosExistentes = duenoRepositorio.findAll();
        for (Dueno dueno : duenosExistentes) {
            String nuevaContrasena = random.ints(48, 123)
                .filter(j -> (j <= 57 || j >= 65) && (j <= 90 || j >= 97))
                .limit(8)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
            dueno.setPassword(nuevaContrasena);
            System.out.println("Contraseña actualizada para " + dueno.getNombre() + ": " + nuevaContrasena);
        }
        duenoRepositorio.saveAll(duenosExistentes);
    }
    
    /**
     * Genera tratamientos adicionales para diversas mascotas
     */
    private void crearTratamientosAdicionales(Dueno[] duenos, MascotaRepositorio mascotaRepositorio, 
                                             VeterinarioRepositorio veterinarioRepositorio,
                                             MedicamentoRepositorio medicamentoRepositorio, 
                                             TratamientoRepositorio tratamientoRepositorio,
                                             Random random) {
        
        System.out.println("\n--- Creando tratamientos adicionales ---");
        
        List<Veterinario> veterinarios = veterinarioRepositorio.findAll();
        List<Medicamento> medicamentos = medicamentoRepositorio.findAll();
        
        if (medicamentos.size() < 10) {
            System.out.println("ADVERTENCIA: Hay menos de 10 medicamentos disponibles.");
            return;
        }
        
        // Descripciones de tratamientos comunes para mascotas
        String[] descripcionesTratamientos = {
            // Tratamientos generales
            "Vacunación anual completa", 
            "Revisión de rutina y evaluación general",
            "Aplicación de microchip",
            "Control de peso y nutrición",
            "Evaluación geriátrica",
            
            // Tratamientos preventivos
            "Vacuna antirrábica",
            "Vacuna polivalente",
            "Tratamiento para parásitos externos", 
            "Tratamiento para parásitos internos",
            "Desparasitación preventiva",
            "Aplicación de antipulgas y garrapatas",
            
            // Tratamientos curativos
            "Tratamiento de infección respiratoria",
            "Tratamiento de infección urinaria", 
            "Tratamiento para problemas digestivos",
            "Tratamiento dermatológico por alergia",
            "Tratamiento para otitis",
            "Control de infecciones respiratorias",
            "Limpieza dental profunda",
            "Curación de herida superficial",
            "Extracción de cuerpo extraño",
            "Terapia para dolores articulares",
            
            // Procedimientos
            "Esterilización/castración",
            "Control de seguimiento post-operatorio",
            "Cuidado post-operatorio de cirugía menor",
            "Radiografía diagnóstica",
            "Análisis de sangre completo",
            "Terapia de comportamiento",
            "Rehabilitación física",
            "Tratamiento oftalmológico"
        };
        
        // Períodos para una distribución más realista
        int[][] periodos = {
            {0, 30},    // último mes
            {31, 90},   // 1-3 meses atrás
            {91, 180},  // 3-6 meses atrás
            {181, 270}, // 6-9 meses atrás
            {271, 365}  // 9-12 meses atrás
        };
        
        // Creamos 100 tratamientos adicionales
        List<Tratamiento> tratamientosAdicionales = new ArrayList<>();
        int totalTratamientos = 100;
        int tratamientosCreados = 0;
        
        // Crear primero un tratamiento para cada combinación de veterinario y periodo
        // Esto asegura una distribución equilibrada
        for (Veterinario veterinario : veterinarios) {
            for (int[] periodo : periodos) {
                if (tratamientosCreados >= totalTratamientos) break;
                
                // Seleccionar un dueño aleatorio con al menos una mascota
                Dueno dueno = null;
                List<Mascota> mascotasDueno = null;
                int intentos = 0;
                
                do {
                    dueno = duenos[random.nextInt(duenos.length)];
                    mascotasDueno = mascotaRepositorio.findByDuenoId(dueno.getIdDueno());
                    intentos++;
                } while (mascotasDueno.isEmpty() && intentos < 10);
                
                if (mascotasDueno.isEmpty()) continue;
                
                // Seleccionar mascota, medicamento, cantidad y fecha
                Mascota mascota = mascotasDueno.get(random.nextInt(mascotasDueno.size()));
                Medicamento medicamento = medicamentos.get(random.nextInt(medicamentos.size()));
                int cantidad = random.nextInt(5) + 1;
                
                // Crear fecha dentro del período
                LocalDate fechaActual = LocalDate.now();
                int diasMinimos = periodo[0];
                int diasMaximos = periodo[1];
                int diasAtras = random.nextInt(diasMaximos - diasMinimos + 1) + diasMinimos;
                LocalDate fechaTratamiento = fechaActual.minusDays(diasAtras);
                
                // Seleccionar descripción adecuada para la especie
                String descripcion = descripcionesTratamientos[random.nextInt(descripcionesTratamientos.length)];
                
                Tratamiento tratamiento = new Tratamiento(
                    fechaTratamiento, 
                    descripcion, 
                    veterinario,
                    mascota, 
                    medicamento, 
                    cantidad
                );
                
                tratamientosAdicionales.add(tratamiento);
                tratamientosCreados++;
                
                System.out.println("Tratamiento #" + tratamientosCreados + ": " + descripcion + 
                                   " para " + mascota.getNombre() + " por " + veterinario.getNombre() + 
                                   " (Período: " + periodo[0] + "-" + periodo[1] + " días atrás)");
            }
        }
        
        // Completar con tratamientos aleatorios hasta llegar a 100
        while (tratamientosCreados < totalTratamientos) {
            // Seleccionar un dueño aleatorio
            Dueno dueno = duenos[random.nextInt(duenos.length)];
            
            // Obtener una mascota de ese dueño
            List<Mascota> mascotasDueno = mascotaRepositorio.findByDuenoId(dueno.getIdDueno());
            if (mascotasDueno.isEmpty()) {
                continue; // Si no tiene mascotas, saltar a la siguiente iteración
            }
            
            Mascota mascota = mascotasDueno.get(random.nextInt(mascotasDueno.size()));
            
            // Seleccionar un veterinario aleatorio
            Veterinario veterinario = veterinarios.get(random.nextInt(veterinarios.size()));
            
            // Seleccionar un medicamento aleatorio
            Medicamento medicamento = medicamentos.get(random.nextInt(medicamentos.size()));
            
            // Crear fechas en diferentes períodos para mejor distribución
            LocalDate fechaActual = LocalDate.now();
            int[] periodo = periodos[random.nextInt(periodos.length)];
            int diasMinimos = periodo[0];
            int diasMaximos = periodo[1];
            int diasAtras = random.nextInt(diasMaximos - diasMinimos + 1) + diasMinimos;
            LocalDate fechaTratamiento = fechaActual.minusDays(diasAtras);
            
            // Seleccionar una descripción aleatoria
            String descripcion = descripcionesTratamientos[random.nextInt(descripcionesTratamientos.length)];
            
            // Generar una cantidad aleatoria de medicamento utilizado (entre 1 y 5)
            int cantidad = random.nextInt(5) + 1;
            
            // Crear el tratamiento
            Tratamiento tratamiento = new Tratamiento(
                fechaTratamiento, 
                descripcion, 
                veterinario, 
                mascota, 
                medicamento, 
                cantidad
            );
            
            tratamientosAdicionales.add(tratamiento);
            tratamientosCreados++;
            
            System.out.println("Tratamiento adicional #" + tratamientosCreados + ": " + descripcion);
        }
        
        // Guardar todos los tratamientos adicionales
        tratamientoRepositorio.saveAll(tratamientosAdicionales);
        System.out.println("Total de tratamientos adicionales creados: " + tratamientosAdicionales.size());
    }

    /**
     * Crea medicamentos adicionales para tener mayor variedad
     */
    private void crearMedicamentosAdicionales(MedicamentoRepositorio medicamentoRepositorio, Random random) {
        System.out.println("\n--- Creando medicamentos adicionales ---");
        
        String[] nombresMedicamentos = {
            // Antibióticos
            "Cefalexina", "Doxiciclina", "Enrofloxacina", "Clindamicina", "Azitromicina",
            
            // Antiparasitarios
            "Praziquantel", "Fenbendazol", "Ivermectina", "Fluralaner", "Moxidectina",
            
            // Antiinflamatorios
            "Meloxicam", "Carprofeno", "Prednisona", "Dexametasona", "Ácido tolfenámico",
            
            // Analgésicos
            "Tramadol", "Gabapentina", "Buprenorfina", "Ketamina", "Butorfanol",
            
            // Otros medicamentos
            "Insulina", "Levotiroxina", "Enalapril", "Furosemida", "Fenobarbital",
            "Ciclosporina", "Omeprazol", "Maropitant", "Apomorfina", "Atenolol"
        };
        
        List<Medicamento> medicamentosAdicionales = new ArrayList<>();
        
        for (String nombre : nombresMedicamentos) {
            double precioCompra = Math.round((random.nextDouble() * 20 + 5) * 100.0) / 100.0; // Entre 5.0 y 25.0
            double precioVenta = precioCompra * (1 + (random.nextDouble() * 0.5 + 0.3)); // Margen entre 30% y 80%
            precioVenta = Math.round(precioVenta * 100.0) / 100.0; // Redondear a 2 decimales
            
            int unidadesDisponibles = random.nextInt(200) + 50; // Entre 50 y 250 unidades
            int unidadesVendidas = random.nextInt(100); // Entre 0 y 100 unidades
            
            Medicamento medicamento = new Medicamento(nombre, precioCompra, precioVenta, unidadesDisponibles, unidadesVendidas);
            medicamentosAdicionales.add(medicamento);
            
            System.out.println("Medicamento adicional creado: " + nombre + " - Precio venta: " + precioVenta);
        }
        
        medicamentoRepositorio.saveAll(medicamentosAdicionales);
        System.out.println("Total de medicamentos adicionales creados: " + medicamentosAdicionales.size());
    }
}