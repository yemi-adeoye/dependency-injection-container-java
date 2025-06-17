package scanner;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;


public class ClassScanner extends ClassVisitor {

    public   List<String> classAnnotations = new ArrayList<>();
    public   List<String> fieldAnnotations = new ArrayList<>();

    public ClassScanner() {
        super(Opcodes.ASM9);
    }


    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible){
        classAnnotations.add(descriptor);
        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value){
        return  new FieldVisitor(Opcodes.ASM9) {
            @Override
            public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                fieldAnnotations.add(descriptor);
                return super.visitAnnotation(descriptor, visible);
            }
        };
    }

    public List<String> getClassAnnotations() {
        return classAnnotations;
    }

    public List<String> getFieldAnnotations() {
        return fieldAnnotations;
    }
}
