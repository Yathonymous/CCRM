
package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.config.DataStore;

import java.util.List;

/**
 * Small polymorphic transcript printer - can be extended to different formats.
 */
public class TranscriptService {
    private final DataStore store = DataStore.getInstance();

    public String buildPlainTranscript(Student s) {
        StringBuilder sb = new StringBuilder();
        sb.append("Transcript - ").append(s.getFullName()).append(" (").append(s.getRegNo()).append(")\n");
        List<Enrollment> ens = store.getEnrollments().stream()
                .filter(e -> e.getStudent().getRegNo().equals(s.getRegNo()))
                .toList();
        if (ens.isEmpty()) sb.append("No enrollments.\n");
        else {
            for (Enrollment e : ens) {
                sb.append(e.getCourse().getCode()).append(" | ").append(e.getCourse().getTitle())
                        .append(" | ").append(e.getCourse().getCredits())
                        .append("cr | grade=").append(e.getGrade() == null ? "N/A" : e.getGrade().toString()).append("\n");
            }
        }
        return sb.toString();
    }
}
