/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.sbmlVisitors

/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

import pt.cnbc.wikimodels.dataModel._
import xml.{XML, Elem}
import pt.cnbc.wikimodels.util.SBMLHandler
import pt.cnbc.wikimodels.exceptions.BadFormatException

/**
 * TODO: Please document.
 * @author Alexandre Martins
 * Date: 07-10-2011
 * Time: 19:13
 */
object Bean2SBMLConverter{
  import scala.collection.JavaConversions._
  //TODO replace _.toXML with this visitor in the code

  def visit(e: Element): Elem = e match {
    case m:SBMLModel => visitModel(m)
    case cp:Compartment => visitCompartment(cp)
    case ct:Constraint => visitConstraint(ct)
    case fd:FunctionDefinition => visitFunctionDefinition(fd)
    case kl:KineticLaw => visitKineticLaw(kl)
    case msr:ModifierSpeciesReference => visitModifierSpeciesReference(msr)
    case p:Parameter => visitParameter(p)
    case r:Reaction => visitReaction(r)
    case s:Species => visitSpecies(s)
    case sr:SpeciesReference => visitSpeciesReference(sr)
    case _ => throw new BadFormatException("Unknow element inside SBMLModel when generating SBML Lvel 2 Version 4")
  }

  def visitModel(m: SBMLModel): Elem = {
    Console.println("SBMLModel.toXML is including the following notes in the model: " + m.notes)
    <model metaid={m.metaid} id={m.id} name={m.name}>
      <!--order is important according to SBML Specifications-->
      {Console.println("Notes of the model are " + m.notes)}
      {SBMLHandler.genNotesFromHTML(m.notes)}
      {if (m.listOfFunctionDefinitions != null && m.listOfFunctionDefinitions.size != 0)
      <listOfFunctionDefinitions>
        {m.listOfFunctionDefinitions.map(i => visitFunctionDefinition(i))}
      </listOfFunctionDefinitions> else scala.xml.Null}
      {if (false)
      <listOfUnitDefinitions>
      </listOfUnitDefinitions> else scala.xml.Null}
      {if (false)
      <listOfCompartmentTypes>
      </listOfCompartmentTypes> else scala.xml.Null}
      {if (false)
      <listOfSpeciesTypes>
      </listOfSpeciesTypes> else scala.xml.Null}
      {if (m.listOfCompartments != null && m.listOfCompartments.size != 0)
      <listOfCompartments>
        {m.listOfCompartments.map(i => visitCompartment(i))}
      </listOfCompartments> else scala.xml.Null}
      {if (m.listOfSpecies != null && m.listOfSpecies.size != 0)
      <listOfSpecies>
        {m.listOfSpecies.map(i => visitSpecies(i))}
      </listOfSpecies> else scala.xml.Null}
      {if (m.listOfParameters != null && m.listOfParameters.size != 0)
      <listOfParameters>
        {m.listOfParameters.map(i => visitParameter(i))}
      </listOfParameters> else scala.xml.Null}
      {if (false)
      <listOfInitialAssignments>
      </listOfInitialAssignments> else scala.xml.Null}
      {if (false)
      <listOfRules>
      </listOfRules> else scala.xml.Null}
      {if (m.listOfConstraints != null && m.listOfConstraints.size != 0)
      <listOfConstraints>
        {m.listOfConstraints.map(i => visitConstraint(i))}
      </listOfConstraints> else scala.xml.Null}
      {if (m.listOfReactions != null && m.listOfReactions.size != 0)
      <listOfReactions>
        {m.listOfReactions.map(i => visitReaction(i))}
      </listOfReactions> else scala.xml.Null}
      {if (false)
      <listOfEvents>
      </listOfEvents> else scala.xml.Null}
    </model>
  }

  def visitCompartment(c: Compartment): Elem =
    <compartment metaid={c.metaid} id={c.id} name={c.name}
                 spatialDimensions={c.spatialDimensions.toString}
                 size={
                 if(c.size == null) null else c.size.toString
                 }
                 units={c.units}
                 outside={c.outside}
                 constant={c.constant.toString}>
      {SBMLHandler.genNotesFromHTML(c.notes)}
    </compartment>


  def visitConstraint(ct: Constraint): Elem =
    <constraint metaid={ct.metaid} id={ct.id} name={ct.name}>
      <!--order is important according to SBML Specifications-->
      {SBMLHandler.genNotesFromHTML(ct.notes)}
      {XML.loadString(ct.math)}
      {SBMLHandler.genMessageFromHTML(ct.message)}
    </constraint>


  def visitFunctionDefinition(fd: FunctionDefinition): Elem =
    <functionDefinition metaid={fd.metaid} id={fd.id} name={fd.name}>
      {SBMLHandler.genNotesFromHTML(fd.notes)}
      {XML.loadString(fd.math)}
    </functionDefinition>


  def visitKineticLaw(kl: KineticLaw): Elem =
    <kineticLaw metaid={kl.metaid}>
      <!--order is important according to SBML Specifications-->
      {SBMLHandler.genNotesFromHTML(kl.notes)}
      {XML.loadString(kl.math)}
      {if(kl.listOfParameters != null && kl.listOfParameters.size != 0)
      <listOfParameters>
        {kl.listOfParameters.map(i => visitParameter(i))}
      </listOfParameters> else scala.xml.Null }
    </kineticLaw>


  def visitModifierSpeciesReference(msr: ModifierSpeciesReference): Elem =
    <modifierSpeciesReference metaid={msr.metaid} id={msr.id} name={msr.name}
                              species={msr.species}>
      {SBMLHandler.genNotesFromHTML(msr.notes)}
    </modifierSpeciesReference>


  def visitParameter(p: Parameter): Elem =
    <parameter metaid={p.metaid} id={p.id} name={p.name}
               value={
               if (p.value == null) null else {p.value.toString}
               }
               units={p.units}
               constant={p.constant.toString}>
      {SBMLHandler.genNotesFromHTML(p.notes)}
    </parameter>



  def visitReaction(r: Reaction): Elem =
    <reaction metaid={r.metaid} id={r.id} name={r.name} >
      {SBMLHandler.genNotesFromHTML(r.notes)}
      {if(r.listOfReactants != null && r.listOfReactants.size != 0)
      <listOfReactants>
        {r.listOfReactants.map(i => visitSpeciesReference(i))}
      </listOfReactants> else scala.xml.Null
      }
      {if(r.listOfProducts != null && r.listOfProducts.size != 0)
      <listOfProducts>
        {r.listOfProducts.map(i => visitSpeciesReference(i))}
      </listOfProducts> else scala.xml.Null
      }
      {if(r.listOfModifiers != null && r.listOfModifiers.size != 0)
      <listOfModifiers>
        {r.listOfModifiers.map(i => visitModifierSpeciesReference(i))}
      </listOfModifiers> else scala.xml.Null
      }
      {if(r.kineticLaw != null)
      r.kineticLaw.toXML
    else
      null.asInstanceOf[Elem]}
    </reaction>


  def visitSpecies(s: Species): Elem =
    <species metaid={s.metaid} id={s.id} name={s.name}
             speciesType={s.speciesType} compartment={s.compartment}
             initialAmount={
             if(s.initialAmount == null) null else {s.initialAmount.toString}
             }
             initialConcentration={
             if(s.initialConcentration == null) null else {s.initialConcentration.toString}
             }
             substanceUnits={s.substanceUnits}
             hasOnlySubstanceUnits={s.hasOnlySubstanceUnits.toString}
             boundaryCondition={s.boundaryCondition.toString}
             constant={s.constant.toString} >
      <!--order is important according to SBML Specifications-->
      {SBMLHandler.genNotesFromHTML(s.notes)}
    </species>


  def visitSpeciesReference(sr: SpeciesReference): Elem = {
    val mathExists = sr.stoichiometryMath.size > 0

    <speciesReference metaid={sr.metaid} id={sr.id} name={sr.name}
                      species={sr.species} stoichiometry=
    {
    if(mathExists) null.asInstanceOf[String] else {sr.stoichiometry.toString}
    }>
      <!--order is important according to SBML Specifications-->
      {SBMLHandler.genNotesFromHTML(sr.notes)}
      {if(mathExists){
      <stoichiometryMath>
        {XML.loadString(sr.stoichiometryMath)}
      </stoichiometryMath>
    } else {
      scala.xml.Null
    }
      }
    </speciesReference>
  }

}