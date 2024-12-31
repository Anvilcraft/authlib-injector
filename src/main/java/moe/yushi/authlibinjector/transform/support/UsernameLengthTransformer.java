package moe.yushi.authlibinjector.transform.support;

import static org.objectweb.asm.Opcodes.ASM9;
import static org.objectweb.asm.Opcodes.ISTORE;
import static org.objectweb.asm.Opcodes.SIPUSH;

import java.util.Optional;
import moe.yushi.authlibinjector.transform.TransformContext;
import moe.yushi.authlibinjector.transform.TransformUnit;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class UsernameLengthTransformer implements TransformUnit {
    @Override
    public Optional<ClassVisitor> transform(
        ClassLoader classLoader,
        String className,
        ClassVisitor writer,
        TransformContext context
    ) {
        if ("io/netty/buffer/ByteBuf".equals(context.getSuperName())
            && !className.startsWith("io.netty")) {
            return Optional.of(new ClassVisitor(ASM9, writer) {
                @Override
                public MethodVisitor visitMethod(
                    int access,
                    String name,
                    String descriptor,
                    String signature,
                    String[] exceptions
                ) {
                    MethodVisitor delegate = super.visitMethod(
                        access, name, descriptor, signature, exceptions
                    );
                    if ("(I)Ljava/lang/String;".equals(descriptor))
                        return new ReadStringVisitor(delegate, context);

                    return delegate;
                }
            });
        }

        return Optional.empty();
    }

    @Override
    public String toString() {
        return "Username Length Transformer";
    }

    public static class ReadStringVisitor extends MethodVisitor {
        TransformContext context;

        protected ReadStringVisitor(MethodVisitor delegate, TransformContext ctx) {
            super(ASM9, delegate);
            context = ctx;
        }

        @Override
        public void visitCode() {
            super.visitCode();
            context.markModified();
            super.visitIntInsn(SIPUSH, Short.MAX_VALUE);
            super.visitVarInsn(ISTORE, 1);
        }
    }
}
