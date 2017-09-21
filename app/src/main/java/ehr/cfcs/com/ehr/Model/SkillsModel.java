package ehr.cfcs.com.ehr.Model;

/**
 * Created by Admin on 21-09-2017.
 */

public class SkillsModel
{
    public String skill;
    public String proficency;
    public String source;
    public String lastUsed;
    public String currentUsed;

    public SkillsModel(String skill, String proficency, String source, String lastUsed, String currentUsed) {
        this.skill = skill;
        this.proficency = proficency;
        this.source = source;
        this.lastUsed = lastUsed;
        this.currentUsed = currentUsed;
    }

    public String getSkill() {
        return skill;
    }

    public String getProficency() {
        return proficency;
    }

    public String getSource() {
        return source;
    }

    public String getLastUsed() {
        return lastUsed;
    }

    public String getCurrentUsed() {
        return currentUsed;
    }
}
