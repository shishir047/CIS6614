/*
 * Copyright (C) 2014, United States Government, as represented by the
 * Administrator of the National Aeronautics and Space Administration.
 * All rights reserved.
 *
 * The Java Pathfinder core (jpf-core) platform is licensed under the
 * Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0. 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package gov.nasa.jpf.jvm.bytecode;

import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.StackFrame;
import gov.nasa.jpf.vm.ThreadInfo;


/**
 * Boolean OR int
 * ..., value1, value2 => ..., result
 */
public class IOR extends Instruction implements JVMInstruction {

  @Override
  public Instruction execute (ThreadInfo ti) {
    StackFrame frame = ti.getModifiableTopFrame();

    int v1 = frame.pop();
    int v2 = frame.pop();

    frame.push(v1 | v2);

    return getNext(ti);
  }

  @Override
  public int getByteCode () {
    return 0x80;
  }
  
  @Override
  public void accept(JVMInstructionVisitor insVisitor) {
	  insVisitor.visit(this);
  }
}
