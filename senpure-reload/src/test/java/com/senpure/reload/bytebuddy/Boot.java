package com.senpure.reload.bytebuddy;

import com.senpure.base.AppEvn;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.asm.AsmVisitorWrapper;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.jar.asm.*;
import net.bytebuddy.utility.OpenedClassReader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Boot
 *
 * @author senpure
 * @time 2020-10-22 17:35:28
 */
public class Boot {
    public static void main(String[] args) {

        Add add = new Add();

        int x = 1;
        int y = 1;
        System.out.println(add.add(x, y));
        ByteBuddyAgent.install();

        ByteBuddy byteBuddy = new ByteBuddy();
        byteBuddy.redefine(Add.class)
                .visit(new AsmVisitorWrapper.ForDeclaredMethods())
                .make();

        byte[] bytes = ClassFileLocator.ForClassLoader.read(Add.class);
        ClassReader classReader = new ClassReader(bytes);
        ClassWriter classWriter = new ClassWriter(0);
        MyClassVisitor classVisitor = new MyClassVisitor(classWriter);
        classReader.accept(classVisitor, 0);
        File file = new File(AppEvn.getClassRootPath(Add.class), "AA.class");
        System.out.println(file.getAbsoluteFile());

        try {
            FileUtils.writeByteArrayToFile(file, classWriter.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(add.add(x, y));
    }

    static class MyClassVisitor extends ClassVisitor {


        public MyClassVisitor(ClassWriter classWriter) {
            super(OpenedClassReader.ASM_API, classWriter);

        }


        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {

            return new MyMethodVisitor(super.visitMethod(access, name, descriptor, signature, exceptions));
        }


    }

    static class MyMethodVisitor extends MethodVisitor {

        public MyMethodVisitor(MethodVisitor methodVisitor) {

            super(OpenedClassReader.ASM_API, methodVisitor);
        }

        @Override
        public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
            System.out.println("visitLocalVariable " + "name = " + name + ", descriptor = " + descriptor + ", signature = " + signature + ", start = " + start + ", end = " + end + ", index = " + index);
            super.visitLocalVariable(name, descriptor, signature, start, end, index);
        }

        @Override
        public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
            System.out.println("visitFrame " + "type = " + type + ", numLocal = " + numLocal + ", local = " + Arrays.deepToString(local) + ", numStack = " + numStack + ", stack = " + Arrays.deepToString(stack));
            super.visitFrame(type, numLocal, local, numStack, stack);
        }

        @Override
        public void visitInsn(int opcode) {
            System.out.println("visitInsn "+ "opcode = " + opcode);
            super.visitInsn(opcode);
        }
    }
}
