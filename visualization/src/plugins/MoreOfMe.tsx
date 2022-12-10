import React from 'react';
import './MoreOfMePlugin.css';
import PersonalPagePlugin from '../framework/PersonalPagePlugin';
import Resume from '../framework/Resume';
import { Icon } from '@iconify/react';

function AboutMePlugin(): PersonalPagePlugin {
    const name: string = "MoreOfMe"

    return {
        getName(): string {
            return name;
        },

        getContent(resume: Resume): JSX.Element {
            const linkedinUrl = "https://" + resume.url;
            return (
                <div className="MoreOfMePlugin">
                    <div className="SkillSets">
                        <h2>My Skill Sets</h2>
                        <div className="SkillSetsContent">
                            <div className="SkillSet">
                                <ul>
                                    {resume.skills.map((skillSet, index) => (
                                        <li key={index}><h4>{skillSet}</h4></li>
                                    ))}
                                </ul>
                            </div>
                        </div>
                    </div>
                    <br />
                    <div className="ContactInfo">
                        <h2>Contact Me</h2>
                        <div className="ContactInfoContent">
                            <div className="ContactInfoItem">
                                <h3><Icon icon="material-symbols:perm-phone-msg" height="1.5em"/> <a href={"tel:" + resume.phoneNumber}>{resume.phoneNumber}</a></h3>
                                <h3><Icon icon="material-symbols:attach-email" height="1.5em"/> <a href={"mailto:" + resume.email}>{resume.email}</a></h3>
                                <h3><Icon icon="mdi:linkedin" height="1.5em"/> <a href={linkedinUrl}>{linkedinUrl}</a></h3>
                            </div>
                        </div>
                    </div>
                </div>
            )
        }
    }
}

export default AboutMePlugin;