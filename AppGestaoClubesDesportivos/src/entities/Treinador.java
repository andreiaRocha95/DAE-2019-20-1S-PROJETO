package entities;

import entities.UserGroup.GROUP;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
    @NamedQuery(name = "getAllStudents", query = "SELECT s FROM Student s ORDER BY s.name"),
    @NamedQuery(name = "getAllCourseStudents", query = "SELECT s FROM Student s WHERE s.course.code = :courseCode ORDER BY s.name")
})
public class Student extends User {

    @ManyToOne
    @JoinColumn(name = "COURSE_CODE")
    private Course course;
    
    @ManyToMany(mappedBy = "students")
    private List<Subject> subjects;

    @OneToMany(mappedBy = "student")
    public List<Document> documents;
    
    protected Student() {
        subjects = new LinkedList<>();
        documents = new LinkedList<>();
    }

    public Student(String username, String password, String name, String email, Course course) {
        super(username, password, GROUP.Student, name, email);
        this.course = course;
        subjects = new LinkedList<>();
        documents = new LinkedList<>();
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public void removeSubject(Subject subject) {
        subjects.remove(subject);
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
    
    public void addDocument(Document document) {
        this.documents.add(document);
    }
    
    public void removeDocument(Document document) {
        this.documents.remove(document);
    }
}
