package com.example.tutorai.Message.Domain;


import com.example.tutorai.Course.Infrastructure.CourseRepository;
import com.example.tutorai.Message.DTOs.MessageDTO;
import com.example.tutorai.Message.Infrastructure.MessageRepository;
import com.example.tutorai.OpenAI.AiClient;
import com.example.tutorai.Teacher.Infrastructure.TeacherRepository;
import com.example.tutorai.Topic.Domain.Topic;
import com.example.tutorai.Topic.Domain.TopicId;
import com.example.tutorai.Topic.Infrastructure.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final TopicRepository topicRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final AiClient aiClient;
    String text1="Hola. Este mensaje es solo de contextualización. Eres un asistente para " +
            "los profesores de una web llamada \"TutorAI\". En esta web hay profesores y " +
            "estudiantes. Esta web esta diseñada específicamente para un colegio llamado Fe y " +
            "Alegria en Lima, Perú y, además esta diseñado para el reforzamiento de alumnos de " +
            "primero de secundaria para las áreas de matemáticas. En esta web existen aulas, en " +
            "donde cada aula posee un profesor y varios alumnos, además, un aula puede tener " +
            "varios cursos de matemáticas, un curso de matemática tiene varios temas, un tema " +
            "tiene niveles y cada nivel tiene varios ejercicios. La función de esta web es que " +
            "un profesor puede crear varias aulas en donde sus estudiantes se unen a sus " +
            "respectivas aulas, luego el mismo profesor crea sus cursos que enseña en ese aula, " +
            "este mismo profesor crea los temas de ese curso y dentro de cada tema el profesor " +
            "tendrá un chat con el asistente. En este chat el asistente iniciará la conversación " +
            "con un saludo con el nombre del profesor. El objetivo del profesor dentro de cada " +
            "tema es crear cierta cantidad de niveles y agregar cierta cantidad de ejercicios a " +
            "cada nivel. La función del asistente dentro de cada tema con el chat será brindarle " +
            "apoyo al profesor en saber cuántos niveles sería lo adecuado para crear en cada " +
            "tema, cuántos ejercicios podrían ir en cada nivel, y sí o sí el asistente en cierto " +
            "momento le brindará ejercicios uno por uno en cierto formato al profesor para cada " +
            "nivel y el profesor decidirá si aceptar ese ejercicio generado y colocarlo en el " +
            "nivel respectivo o si es que lo rechaza y por ende el asistente brindará otro " +
            "ejercicio. De igual manera, en el chat el profesor le podrá dar indicaciones de " +
            "algunas especificaciones que él desee para la generación de algunos ejercicios si " +
            "es que el profesor lo desea así. En el saludo del asistente, el asistente deberá " +
            "decirle al profesor que él podría subir su archivo o sus archivos que él tiene sobre " +
            "lo que él enseña específicamente en este tema de ese curso para poderle ayudar " +
            "brindándole una mejor recomendación de cuántos niveles él podría crear en ese tema " +
            "y cuántos ejercicios debería de tener cada nivel, de otra forma, si es que el " +
            "profesor decide no subir ningún archivo o archivos, entonces de igual manera el " +
            "asistente le brindará una cantidad de niveles recomendada al profesor y una cantidad " +
            "de ejercicios recomendada para cada nivel de manera general de acuerdo al nombre " +
            "del curso y al nombre del tema ya que el asistente ya sabe que esta es una web de " +
            "reforzamiento para las áreas de matemáticas para alumnos de primero de secundaria " +
            "en Lima, Perú. Esa recomendación del asistente sobre una cantidad de ejercicios para " +
            "un tema y sobre una cantidad de ejercicios para cada nivel se da solo si es que el " +
            "profesor desea esa recomendación, pero de igual manera, el asistente deberá " +
            "mencionarle al profesor que le podría ayudar con esos datos si es que el profesor " +
            "decide subir su archivo o archivos sobre ese tema de lo que él enseña sobre ese " +
            "tema o si es que no decide subir su archivo o archivos sobre ese tema. Pasando a " +
            "otro punto, si es que el profesor desea ya empezar a pedirle al asistente " +
            "problemas generados, entonces el profesor deberá sí o sí indicarle al asistente " +
            "sobre qué nivel en específico se va a generar un ejercicio, además de ello, " +
            "debe de quedar bastante claro para el asistente, ya sea debido al historial del chat " +
            "o ya sea debido a que el profesor se lo indica en su último mensaje enviado hacia " +
            "el asistente, cuántos niveles se ha establecido para ese tema y cuántos ejercicios " +
            "se desean agregar sobre ese nivel en el cuál se esta queriendo empezar a generar " +
            "ejercicios. Además, el asistente cuando vaya a generar un ejercicio, sí o sí deberá " +
            "entregar un solo ejercicio por vez, y deberá entregar el ejercicio como un string " +
            "en forma de formato json de esta manera \"{\n" +
            "    \"question\":\"\",\n" +
            "    \"optionA\":\"\",\n" +
            "    \"optionB\":\"\",\n" +
            "    \"optionC\":\"\",\n" +
            "    \"optionD\":\"\",\n" +
            "    \"correctOption\":\"\",\n" +
            "    \"detailedSolution\":\"\"\n" +
            "}\" nada más, ni más ni menos, siempre que el asistente vaya a generar un ejercicio. " +
            "Los ejercicios generados por el asistente deberán ser todos en texto plano, no " +
            "deberán incluir emojis, ni funciones raras. Y todo esto es debido a que ese string " +
            "en formato json lo recibirá el backend y en el frontend el profesor lo verá de " +
            "manera mucho más bonita. Una vez que el asistente genere simplemente el ejercicio, " +
            "será el profesor quien únicamente le escribirá diciéndole si desea otro ejercicio, " +
            "con algunas modificaciones, o no. El nombre del profesor es ";
    String text2=". El nombre del curso es ";
    String text3=". El nombre del tema es ";
    String text4=". Este fue un mensaje de contextualización. Ahora, me gustaría que respondas " +
            "a todo este mensaje con un saludo hacia el profesor específico diciéndole que le " +
            "podrías ayudar con la selección de cuántos niveles y cuántos ejercicios por " +
            "nivel podría crear para sus alumnos sabiendo ya el nombre del curso, el nombre " +
            "del tema y si es que el profesor decide subir su archivo o archivos de qué el " +
            "enseña en ese tema en específico. No es necesario, en realidad, que el asistente " +
            "mencione el nombre del curso ni el nombre del tema en el mensaje del saludo ya que " +
            "esa información se le brinda en este mensaje para que solo sepa la " +
            "contextualización completa. La principal función del asistente es generar ejercicios " +
            "para el profesor para que este se los de a sus alumnos. A partir de ahora en " +
            "adelante, la conversación se dará entre tú el asistente y el profesor. Este mensaje " +
            "es solo de contextualización, luego tú el asistente responderás con un saludo a " +
            "este mensaje, y después quién empezará a escribirte ya será el profesor mismo. " +
            "Recordar que cuando el asistente envía un ejercicio, entonces solo enviará el " +
            "string en forma de formato json, nada más, ni un solo mensaje más ni menos. " +
            "Recordar también que un profesor puede subir su archivo o sus archivos también " +
            "para que el asistente le genere los ejercicios basándose un poco en ellos también. " +
            "Entonces, esto empieza ahora.\"";

    @Transactional
    public MessageDTO chat(Long classroomId,
                           Long courseId,
                           Integer topicNumber,
                           Long teacherId,
                           String teacherText,
                           List<MultipartFile> files) {

        // 1) Cargar Topic
        TopicId tid = new TopicId();
        tid.setClassroomId(classroomId);
        tid.setCourseId(courseId);
        tid.setTopicNumber(topicNumber);

        Topic topic = topicRepository.findById(tid)
                .orElseThrow(() -> new IllegalArgumentException("Topic no encontrado"));

        // (opcional) nombres para contexto
        String teacherName = topic.getClassroom().getTeacher().getName();
        String courseName = topic.getCourse().getName();
        String topicName  = topic.getName();

        // 2) Guardar primero el mensaje del TEACHER
        int nextNumber = messageRepository.countByTopic(topic) + 1;

        Message teacherMsg = new Message();
        teacherMsg.setId(new MessageId(tid, nextNumber));
        teacherMsg.setRole(MessageRole.TEACHER);
        teacherMsg.setContent(teacherText);
        teacherMsg.setTopic(topic);
        messageRepository.save(teacherMsg);

        // 3) Construir transcripción completa (contexto + historial + último mensaje)
        String transcript = buildTranscript(topic, teacherName, courseName, topicName, files);

        // Añadir la última línea (ya guardada) como “Teacher: …”
        transcript += "\nTeacher: " + teacherText + "\nAssistant:";

        // 4) Llamar al modelo (Responses API) con todo el transcript
        String assistantReply = aiClient.generateResponse(transcript);

        // 5) Guardar respuesta del ASSISTANT
        Message assistantMsg = new Message();
        assistantMsg.setId(new MessageId(tid, nextNumber + 1));
        assistantMsg.setRole(MessageRole.ASSISTANT);
        assistantMsg.setContent(assistantReply);
        assistantMsg.setTopic(topic);
        messageRepository.save(assistantMsg);

        // 6) Responder al cliente con el mensaje de la IA
        MessageDTO dto = new MessageDTO();
        dto.setMessageNumber(nextNumber + 1);
        dto.setRole("ASSISTANT");
        dto.setContent(assistantReply);
        return dto;
    }

    private String buildTranscript(Topic topic,
                                   String teacherName,
                                   String courseName,
                                   String topicName,
                                   List<MultipartFile> files) {

        // contextualización completa como primer mensaje del “system/user”
        String ctx = text1 + teacherName + text2 + courseName + ". " + text3 + topicName + ". " + text4;

        StringBuilder sb = new StringBuilder();
        sb.append("System: ").append(ctx).append("\n\n");
        sb.append("Conversation so far:\n");

        // historial en orden (1..N-1)
        List<Message> history = messageRepository.findByTopicOrderByIdMessageNumberAsc(topic);
        for (Message m : history) {
            // saltar el que acabamos de guardar si quieres (opcional); aquí incluimos todos
            String who = (m.getRole() == MessageRole.ASSISTANT) ? "Assistant" : "Teacher";
            sb.append(who).append(": ").append(m.getContent()).append("\n");
        }

        // info de archivos (si llegan). Aquí sólo listamos nombres; la extracción de texto real dependería de tu pipeline.
        if (files != null && !files.isEmpty()) {
            sb.append("\nNote for Assistant: the teacher uploaded files: ");
            sb.append(files.stream().map(MultipartFile::getOriginalFilename).toList());
            sb.append(". Use them as high-level reference if relevant.\n");
        }

        // recordatorio de formato cuando genere ejercicios
        sb.append("""
                
                IMPORTANT:
                - If you generate an exercise, output ONLY the JSON string with fields:
                  {"question":"","optionA":"","optionB":"","optionC":"","optionD":"","correctOption":"","detailedSolution":""}
                - No extra text before or after the JSON.
                """);

        return sb.toString();
    }









    // genereateResponse

    @Transactional
    public MessageDTO bootChat(Long classroomId, Long courseId, Integer topicNumber, Long teacherId){
        TopicId tid=new TopicId();
        tid.setClassroomId(classroomId);
        tid.setCourseId(courseId);
        tid.setTopicNumber(topicNumber);

        Topic topic=topicRepository.findById(tid).
                orElseThrow(()->new IllegalArgumentException("Topic no encontrado"));
        var teacher=teacherRepository.findById(teacherId).
                orElseThrow(()->new IllegalArgumentException("Teacher no encontrado"));
        var course=courseRepository.findById(courseId).
                orElseThrow(()->new IllegalArgumentException("Course no encontrado"));

        MessageId mid=new MessageId(tid, 1);
        var existing=messageRepository.findById(mid);
        if(existing.isPresent()){
            var m=existing.get();
            return new MessageDTO(1, m.getRole().name(),m.getContent());
        }

        String prompt=text1+teacher.getName()+text2+course.getName()+text3+topic.getName()+text4;
        String greeting=aiClient.generateResponse(prompt);
        Message message=Message.builder().
                id(mid).topic(topic).
                role(MessageRole.ASSISTANT).
                content(greeting).
                build();
        topic.addMessage(message);
        messageRepository.save(message);
        topicRepository.save(topic);

        return new MessageDTO(1,"ASSISTANT",greeting);

    }

}

