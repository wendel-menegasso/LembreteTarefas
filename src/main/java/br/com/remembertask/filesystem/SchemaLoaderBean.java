package br.com.remembertask.filesystem;

import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;
import java.io.InputStream;

@Singleton
@Startup
public class SchemaLoaderBean {

    private static Schema schema;

    @PostConstruct
    public static void init() {
        try {
            // Carrega XML e XSD do classpath
            InputStream xmlStream = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("schema.xml");
            InputStream xsdStream = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("schema.xsd");

            if (xmlStream == null || xsdStream == null) {
                throw new RuntimeException("schema.xml ou schema.xsd não encontrados no classpath!");
            }

            // Configura JAXB com validação
            JAXBContext context = JAXBContext.newInstance(Schema.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            javax.xml.validation.Schema schemaValidator =
                    SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema")
                            .newSchema(new javax.xml.transform.stream.StreamSource(xsdStream));

            unmarshaller.setSchema(schemaValidator);

            // Faz o unmarshal com validação
            schema = (Schema) unmarshaller.unmarshal(xmlStream);

            System.out.println("Schema carregado e validado com sucesso!");
            schema.getFields().forEach(f ->
                    System.out.println("Campo " + f.getOrder() + ": " + f.getName() + " (tamanho " + f.getSize() + ")")
            );

        } catch (SAXException e) {
            throw new RuntimeException("Erro de validação do XML contra o XSD: " + e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Schema getSchema() {
        return schema;
    }
}